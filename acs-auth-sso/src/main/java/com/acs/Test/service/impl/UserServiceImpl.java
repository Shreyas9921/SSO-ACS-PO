package com.acs.Test.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

import com.acs.Test.repository.UsersRepository;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.acs.common.cache.CacheContainer;
import com.acs.common.cache.CacheNames;
import com.acs.common.dto.LoginAttemptsDto;
import com.acs.common.dto.UsersAuthDto;
import com.acs.common.enums.DeviceType;
import com.acs.common.exception.IllegalArgumentException;
import com.acs.common.exception.NotAuthenticatedException;
import com.acs.common.exception.NotAuthorizedException;
import com.acs.common.logger.LogManager;
import com.acs.common.logger.Logger;
import com.acs.common.logger.MessageType;
import com.acs.common.service.ILoginAttemptsServiceCache;
import com.acs.common.service.IUserAuthServiceCache;
import com.acs.common.service.IUserDetailsServiceCache;
import com.acs.common.utils.AuthUtils;
import com.acs.common.utils.AuthUtilsKeyCloak;
import com.acs.common.utils.CommonFunctions;
import com.acs.common.utils.Constant;
import com.acs.common.utils.StringUtils;
import com.acs.Test.commons.utils.ErrorMessages;
import com.acs.Test.dao.IUserDao;
import com.acs.Test.dto.LoginRequestDto;
import com.acs.Test.dto.Users;
import com.acs.Test.service.UserService;
import com.acs.Test.enums.Status;
import org.springframework.transaction.annotation.Transactional;

@Service
// @Transactional(transactionManager = "authTransactionManager") // Use Auth transaction manager
public class UserServiceImpl implements UserService {

	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

	@Value("${keycloak.realm}")
	private String realm;

	@Value("${keycloak.credentials.username}")
	private String userName;

	@Value("${keycloak.enable:false}")
	private Boolean enableSSO;

	@Value("${login.attempts}")
	private Integer loginAttempts;

	@Autowired
	private IUserDao userDao;

	@Autowired
	@Qualifier("authUtils")
	private AuthUtils authUtils;

	@Autowired
	private AuthzClient keycloakAuthClient;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private IUserAuthServiceCache userAuthServiceCache;

	@Autowired
	private ILoginAttemptsServiceCache loginAttemptsServiceCache;

	@Autowired
	private IUserDetailsServiceCache userDetailsServiceCache;

	@PostConstruct
	public void initService() {
		LOG.info("Redis User Details Initial Data Scheduled");
		UsersAuthDto authDto = null;
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				LOG.info("Redis Initial Data Loading");
				List<com.acs.Test.pojo.Users> userList = userDao.findAll();
				userDetailsServiceCache.saveAllUsers(userList.parallelStream().map(usr -> userDetailsBuilder(authDto, usr))
						.collect(Collectors.toMap(UsersAuthDto::getId, Function.identity())));
			}
		}, 50000);
	}

	private UsersAuthDto userDetailsBuilder(UsersAuthDto userDetails, com.acs.Test.pojo.Users usr) {
		if (Objects.nonNull(userDetails)) {
			// Fetch Users Role information


			return UsersAuthDto.builder().id(usr.getId()).userName(usr.getUserName())

					.lastName(usr.getLastName())
					.name(usr.getFirstName()
							+ (StringUtils.isBlank(usr.getLastName()) ? "" : (" " + usr.getLastName())))
					.userEmail(usr.getEmail()).build();
		} else {
			return UsersAuthDto.builder().id(usr.getId()).userName(usr.getUserName()).firstName(usr.getFirstName())
					.lastName(usr.getLastName())
					.name(usr.getFirstName()
							+ (StringUtils.isBlank(usr.getLastName()) ? "" : (" " + usr.getLastName())))
					.userEmail(usr.getEmail()).build();
		}
	}

	public Users userLogin(LoginRequestDto loginData, DeviceType deviceType, String userAgent, String appVersion)
			throws NotAuthorizedException, IOException, IllegalArgumentException, NotAuthenticatedException {

		// check cache for login attempts
		LoginAttemptsDto loginAttemptsDto = loginAttemptsServiceCache.getUserByUserName(loginData.getUserName());
		if (Objects.nonNull(loginAttemptsDto) && loginAttempts <= loginAttemptsDto.getLoginAttempts()) {
			Optional<com.acs.Test.pojo.Users> userResult = userDao.findByUserNameIgnoreCase(loginData.getUserName());
			userResult.get().setStatus(Status.INACTIVE.getId());
			usersRepository.save(userResult.get());
			throw new IllegalArgumentException(ErrorMessages.EXCEED_LOGIN_ATTEMPTS);
		}

		Optional<com.acs.Test.pojo.Users> userResult = userDao.findByUserName(loginData.getUserName());
		if (userResult.isPresent()) {
			com.acs.Test.pojo.Users users = userResult.get();
			if (Status.INACTIVE.getId() == users.getStatus()) {
				throw new IllegalArgumentException("User Inactive");
			}
		}

		if (Objects.isNull(loginAttemptsDto)) {
			loginAttemptsDto = new LoginAttemptsDto();
		}
		// if(userName.equalsIgnoreCase(loginData.getUserName())) {
		loginAttemptsDto.setUserName(loginData.getUserName());
		loginAttemptsServiceCache.updateUser(loginAttemptsDto);
		// }

		//
		AccessTokenResponse response = null;
		try {
			response = keycloakAuthClient.obtainAccessToken(loginData.getUserName(), loginData.getPassword());
		} catch (Exception e) {
			loginAttemptsDto.setLoginAttempts(
					((Objects.nonNull(loginAttemptsDto) && Objects.nonNull(loginAttemptsDto.getLoginAttempts()))
							? loginAttemptsDto.getLoginAttempts() + 1
							: 1));
			loginAttemptsServiceCache.updateUser(loginAttemptsDto);
			LOG.error(e.getLocalizedMessage(), e);
			throw new NotAuthenticatedException("Invalid UserName Password");
		}
		LOG.info("AccessTokenResponse :: " + CommonFunctions.objectToJson(response));

		AuthUtilsKeyCloak auth = new AuthUtilsKeyCloak();
		Map<String, String> userDetails = auth.getUserDetails(response.getToken());
		String userId = userDetails.getOrDefault(Constant.KEYCLOAK_USER_ID, null);
		String firstName = userDetails.getOrDefault(Constant.KEYCLOAK_FIRSTNAME, null);
		String lastName = userDetails.getOrDefault(Constant.KEYCLOAK_LASTNAME, null);
		String email = userDetails.getOrDefault(Constant.KEYCLOAK_EMAIL, null);
		//String roles = userDetails.getOrDefault(Constant.KEYCLOAK_ROLES, null);

		LOG.info("User Id: {}", userId);

		// Get User Details From User Id
		// UserRepresentation user =
		// keycloakRealmResource.realm(realm).users().get(userId).toRepresentation();
		// LOG.info("User Name: {}", user.getUsername());

		String username = loginData.getUserName();
		Users users = new Users();
		if (CacheContainer.getInstance().isKeyAvailable(username.toLowerCase(), CacheNames.userToken.getName())) {
			users = (Users) CacheContainer.getInstance().getValueFromCache(username.toLowerCase(),
					CacheNames.userToken.getName());
			LogManager.debug(getClass(), users.toString(), MessageType.ApiRequest);
			CacheContainer.getInstance().deleteFromContainer(username.toLowerCase(), CacheNames.userToken.getName());
			CacheContainer.getInstance().addToContainer(username.toLowerCase(), users, CacheNames.userToken.getName());
		}

		String authToken = response.getToken();
		users.setUserId(userId);
		users.setRefreshTokenString(response.getRefreshToken());
		users.setTokenString(authToken);
		users.setUserName(username);
		users.setFirstName(firstName);
		users.setLastName(lastName);
		users.setUserEmail(email);
		// users.setRole(userResult.get().getRoleId());
		users.setId(userResult.get().getId());
		// users.setIsDisplay(userResult.get().getIsDisplay());

		// Save User cache
		userAuthServiceCache.deleteUserName(users.getUserName().toLowerCase());
		userAuthServiceCache.saveUser(
				UsersAuthDto.builder().id(userResult.get().getId()).userName(users.getUserName().toLowerCase())
						.userEmail(users.getUserEmail()).authKey(Constant.AUTH_TOKEN).authValue(users.getTokenString())
						.firstName(users.getFirstName()).lastName(users.getLastName()).deviceType(deviceType)
						.appVersion(appVersion).name(users.getFirstName() + " " + users.getLastName()).build());

		// Fetch Users Role information
		//** If Role Type = 3 and User for Customer Role then fetch customer details


		CacheContainer.getInstance().addToContainer(username.toLowerCase(), users, CacheNames.userToken.getName());
		CacheContainer.getInstance().setRequestKey(authToken);

		//userJourneyDao.update(Logger.getLogger(getClass()).getValue(Constant.REQUEST_ID), null, users.getUserName());
		//users.setshipperAddresss("Testing Address");

		// userAuthServiceCache.deleteUserName(users.getUserName().toLowerCase());
		userAuthServiceCache.updateUser(UsersAuthDto.builder().id(userResult.get().getId())
				.userName(users.getUserName().toLowerCase())
				.userEmail(users.getUserEmail()).authKey(Constant.AUTH_TOKEN).authValue(users.getTokenString())
				.firstName(users.getFirstName()).lastName(users.getLastName()).deviceType(deviceType).appVersion(appVersion)
				.name(users.getFirstName() + " " + users.getLastName()).build());
		loginAttemptsDto.setLoginAttempts(0);
		loginAttemptsServiceCache.updateUser(loginAttemptsDto);
		return users;
	}

	@Override
	public UsersAuthDto getUserAuthDetailsByUserName(String userName) {
		return userAuthServiceCache.getUserByUserName(userName);
	}
}

/*
package com.acs.Test.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.acs.common.cache.CacheContainer;
import com.acs.common.cache.CacheNames;
import com.acs.common.dto.LoginAttemptsDto;
import com.acs.common.dto.UsersAuthDto;
import com.acs.common.enums.DeviceType;
import com.acs.common.exception.IllegalArgumentException;
import com.acs.common.exception.NotAuthenticatedException;
import com.acs.common.exception.NotAuthorizedException;
import com.acs.common.logger.LogManager;
import com.acs.common.logger.Logger;
import com.acs.common.logger.MessageType;
import com.acs.common.service.ILoginAttemptsServiceCache;
import com.acs.common.service.IUserAuthServiceCache;
import com.acs.common.service.IUserDetailsServiceCache;
import com.acs.common.utils.AuthUtils;
import com.acs.common.utils.AuthUtilsKeyCloak;
import com.acs.common.utils.CommonFunctions;
import com.acs.common.utils.Constant;
import com.acs.common.utils.StringUtils;
import com.acs.Test.repository.UsersRepository;
import com.acs.Test.commons.utils.ErrorMessages;
import com.acs.Test.dao.IUserDao;
import com.acs.Test.dto.LoginRequestDto;
import com.acs.Test.dto.Users;
import com.acs.Test.service.UserService;
import com.acs.Test.enums.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

	@Value("${keycloak.realm}")
	private String realm;

	@Value("${keycloak.credentials.username}")
	private String userName;

	@Value("${keycloak.enable:false}")
	private Boolean enableSSO;


	@Value("${login.attempts}")
	private Integer loginAttempts;


	@Autowired
	private IUserDao userDao;

	@Autowired
	@Qualifier("authUtils")
	private AuthUtils authUtils;

	@Autowired
	private AuthzClient keycloakAuthClient;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private IUserAuthServiceCache userAuthServiceCache;

	@Autowired
	private ILoginAttemptsServiceCache loginAttemptsServiceCache;

	@Autowired
	private IUserDetailsServiceCache userDetailsServiceCache;

	@PostConstruct
	public void initService() {
		log.info("Redis User Details Initial Data Scheduled");
		UsersAuthDto authDto = null;
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				log.info("Redis Initial Data Loading");
				List<com.acs.Test.pojo.Users> userList = userDao.findAll();
				userDetailsServiceCache.saveAllUsers(userList.parallelStream().map(usr -> userDetailsBuilder(authDto, usr))
								.collect(Collectors.toMap(UsersAuthDto::getId, Function.identity())));
			}
		}, 50000);
	}

	private UsersAuthDto userDetailsBuilder(UsersAuthDto userDetails, com.acs.Test.pojo.Users usr) {
		if (Objects.nonNull(userDetails)) {
			// Fetch Users Role information
			
			
			return UsersAuthDto.builder().id(usr.getId()).userName(usr.getUserName())
					
					.lastName(usr.getLastName())
					.name(usr.getFirstName()
							+ (StringUtils.isBlank(usr.getLastName()) ? "" : (" " + usr.getLastName())))
					.userEmail(usr.getEmail()).build();
		} else {
			return UsersAuthDto.builder().id(usr.getId()).userName(usr.getUserName()).firstName(usr.getFirstName())
					.lastName(usr.getLastName())
					.name(usr.getFirstName()
							+ (StringUtils.isBlank(usr.getLastName()) ? "" : (" " + usr.getLastName())))
					.userEmail(usr.getEmail()).build();
		}
	}


	public Users userLogin(LoginRequestDto loginData, DeviceType deviceType, String userAgent, String appVersion)
			throws NotAuthorizedException, IOException, IllegalArgumentException, NotAuthenticatedException {

		// check cache for login attempts
		LoginAttemptsDto loginAttemptsDto = loginAttemptsServiceCache.getUserByUserName(loginData.getUserName());
		if (Objects.nonNull(loginAttemptsDto) && loginAttempts <= loginAttemptsDto.getLoginAttempts()) {
			Optional<com.acs.Test.pojo.Users> userResult = userDao.findByUserNameIgnoreCase(loginData.getUserName());
			userResult.get().setStatus(Status.INACTIVE.getId());
			usersRepository.save(userResult.get());
			throw new IllegalArgumentException(ErrorMessages.EXCEED_LOGIN_ATTEMPTS);
		}

		Optional<com.acs.Test.pojo.Users> userResult = userDao.findByUserName(loginData.getUserName());
		if (userResult.isPresent()) {
			com.acs.Test.pojo.Users users = userResult.get();
			if (Status.INACTIVE.getId() == users.getStatus()) {
				throw new IllegalArgumentException("User Inactive");
			}

		}

		if (Objects.isNull(loginAttemptsDto)) {
			loginAttemptsDto = new LoginAttemptsDto();
		}
		// if(userName.equalsIgnoreCase(loginData.getUserName())) {
		loginAttemptsDto.setUserName(loginData.getUserName());
		loginAttemptsServiceCache.updateUser(loginAttemptsDto);
		// }

		//
		AccessTokenResponse response = null;
		try {
			response = keycloakAuthClient.obtainAccessToken(loginData.getUserName(), loginData.getPassword());
		} catch (Exception e) {
			loginAttemptsDto.setLoginAttempts(
					((Objects.nonNull(loginAttemptsDto) && Objects.nonNull(loginAttemptsDto.getLoginAttempts()))
							? loginAttemptsDto.getLoginAttempts() + 1
							: 1));
			loginAttemptsServiceCache.updateUser(loginAttemptsDto);
			log.error(e.getLocalizedMessage(), e);
			throw new NotAuthenticatedException("Invalid UserName Password");
		}
		LOG.info("AccessTokenResponse :: " + CommonFunctions.objectToJson(response));

		AuthUtilsKeyCloak auth = new AuthUtilsKeyCloak();
		Map<String, String> userDetails = auth.getUserDetails(response.getToken());
		String userId = userDetails.getOrDefault(Constant.KEYCLOAK_USER_ID, null);
		String firstName = userDetails.getOrDefault(Constant.KEYCLOAK_FIRSTNAME, null);
		String lastName = userDetails.getOrDefault(Constant.KEYCLOAK_LASTNAME, null);
		String email = userDetails.getOrDefault(Constant.KEYCLOAK_EMAIL, null);
		//String roles = userDetails.getOrDefault(Constant.KEYCLOAK_ROLES, null);

		LOG.info("User Id: {}", userId);

		// Get User Details From User Id
		// UserRepresentation user =
		// keycloakRealmResource.realm(realm).users().get(userId).toRepresentation();
		// LOG.info("User Name: {}", user.getUsername());

		String username = loginData.getUserName();
		Users users = new Users();
		if (CacheContainer.getInstance().isKeyAvailable(username.toLowerCase(), CacheNames.userToken.getName())) {
			users = (Users) CacheContainer.getInstance().getValueFromCache(username.toLowerCase(),
					CacheNames.userToken.getName());
			LogManager.debug(getClass(), users.toString(), MessageType.ApiRequest);
			CacheContainer.getInstance().deleteFromContainer(username.toLowerCase(), CacheNames.userToken.getName());
			CacheContainer.getInstance().addToContainer(username.toLowerCase(), users, CacheNames.userToken.getName());
		}

		String authToken = response.getToken();
		users.setUserId(userId);
		users.setRefreshTokenString(response.getRefreshToken());
		users.setTokenString(authToken);
		users.setUserName(username);
		users.setFirstName(firstName);
		users.setLastName(lastName);
		users.setUserEmail(email);
		// users.setRole(userResult.get().getRoleId());
		users.setId(userResult.get().getId());
		// users.setIsDisplay(userResult.get().getIsDisplay());

		// Save User cache
		userAuthServiceCache.deleteUserName(users.getUserName().toLowerCase());
		userAuthServiceCache.saveUser(
				UsersAuthDto.builder().id(userResult.get().getId()).userName(users.getUserName().toLowerCase())
						.userEmail(users.getUserEmail()).authKey(Constant.AUTH_TOKEN).authValue(users.getTokenString())
						.firstName(users.getFirstName()).lastName(users.getLastName()).deviceType(deviceType)
						.appVersion(appVersion).name(users.getFirstName() + " " + users.getLastName()).build());

		// Fetch Users Role information

		*/
/**
		 * If Role Type = 3 and User for Customer Role then fetch customer details
		 *//*


		CacheContainer.getInstance().addToContainer(username.toLowerCase(), users, CacheNames.userToken.getName());
		CacheContainer.getInstance().setRequestKey(authToken);

		//userJourneyDao.update(Logger.getLogger(getClass()).getValue(Constant.REQUEST_ID), null, users.getUserName());
		//users.setshipperAddresss("Testing Address");

		// userAuthServiceCache.deleteUserName(users.getUserName().toLowerCase());
		userAuthServiceCache.updateUser(UsersAuthDto.builder().id(userResult.get().getId())
				.userName(users.getUserName().toLowerCase())
				.userEmail(users.getUserEmail()).authKey(Constant.AUTH_TOKEN).authValue(users.getTokenString())
				.firstName(users.getFirstName()).lastName(users.getLastName()).deviceType(deviceType).appVersion(appVersion)
				.name(users.getFirstName() + " " + users.getLastName()).build());
		loginAttemptsDto.setLoginAttempts(0);
		loginAttemptsServiceCache.updateUser(loginAttemptsDto);
		return users;
	}


	@Override
	public UsersAuthDto getUserAuthDetailsByUserName(String userName) {
		return userAuthServiceCache.getUserByUserName(userName);
	}

}*/
