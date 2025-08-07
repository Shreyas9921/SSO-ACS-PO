
package com.acs.Test.commons.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessages {

	public static String INVALID_API_KEY_MESSAGE;

	public static String INVALID_TOKEN_MESSAGE;

	/** The token expired. */
	public static String TOKEN_EXPIRED;

	public static String USERNAME_NOT_FOUND;
	public static String USERNAME_EXISTED;
	public static String EMAIL_EXISTED;
	public static String ROLE_NOT_FOUND;

	public static String InvalidLogin = "Invalid UserName / Password";

	public static String EXCEED_LOGIN_ATTEMPTS = "You have exceed the number of login attempts. Please contact administrator";

	public static final String INVALID_CREDENTIALS = "Password must be minimum 8 and maximum 15 characters, at least one uppercase letter, one lowercase letter, one digit and one special character from allowed characters !,@,#,$";

	public static final String INVALID_PASSWORD = "Password must be minimum 8 and maximum 15 characters, at least one uppercase letter, one lowercase letter, one digit and one special character from allowed characters !,@,#,$";

	public static String INVALID_ACCOUNT_ID_MESSAGE = "Not Authorized, Invalid Account ID";

	public static String INVALID_DEVICE_TYPE;

	public static String INVALID_REQUEST;

	public static String accessPermission;

	public static String INTERNAL_SERVER_ERROR;

	public static String INVALID_USER_LOGIN_ERROR;

	@Value("${error.message.username.existed}")
	public void setUsernameExisted(String errorMessage) {
		USERNAME_EXISTED = errorMessage;
	}
	@Value("${error.message.email.existed}")
	public void setEmailExisted(String errorMessage) {
		EMAIL_EXISTED = errorMessage;
	}
	@Value("${error.message.username.not.found}")
	public void setUsernameNotFound(String errorMessage) {
		USERNAME_NOT_FOUND = errorMessage;
	}
	@Value("${error.message.role.not.found}")
	public void setRoleNotFound(String errorMessage) {
		ROLE_NOT_FOUND = errorMessage;
	}
	@Value("${error.message.invalid.api.key}")
	public void setInvalidApiKeyErrorMessage(String errorMessage) {
		INVALID_TOKEN_MESSAGE = errorMessage;
	}

	@Value("${error.message.token.expired}")
	public void setTokenExpiredErrorMessage(String errorMessage) {
		TOKEN_EXPIRED = errorMessage;
	}

	@Value("${error.message.invalid.token}")
	public void setInvalidTokenErrorMessage(String errorMessage) {
		INVALID_TOKEN_MESSAGE = errorMessage;
	}

	@Value("${error.message.invalid.device.type}")
	public void setInvalidDeviceTypeErrorMessage(String errorMessage) {
		INVALID_DEVICE_TYPE = errorMessage;
	}

	@Value("${error.message.invalid.request}")
	public void setInvalidRequestErrorMessage(String errorMessage) {
		INVALID_REQUEST = errorMessage;
	}

	@Value("${error.message.unknown.error}")
	public void setInternalServerErrorMessage(String errorMessage) {
		INTERNAL_SERVER_ERROR = errorMessage;
	}

	@Value("${error.message.invalid.user}")
	public void setInvalidUserErrorMessage(String errorMessage) {
		INVALID_USER_LOGIN_ERROR = errorMessage;
	}
}
