/*
 * @author Advatix
 * 
 * @since 2019
 * 
 * @version 1.0
 */
package com.acs.Test.commons.utils;

import org.springframework.beans.factory.annotation.Value;

public final class Constant {

	public static final String AUTH_TOKEN = "AUTH-TOKEN";
	
	public static final String ACCOUNT_ID = "AccountId";

	public static final String API_KEY = "ApiKey";

	public static final String API_KEY_ACSECOM = "apiKey";

	public static final String DEVICE_TYPE = "DEVICE-TYPE";

	public static final String APP_VERSION = "VER";
	
	public static final String CONTENT_TYPE = "Content-Type";

	public static final String PIPE_SEPERATOR = "|";
	public static final String COMMA_SEPERATOR = ",";

	/** The upload path. */
	public static final String UPLOAD_PATH = "/uploads";

	/** The file upload api path. */
	public static final String[] FILE_UPLOAD_API_PATH = { UPLOAD_PATH };

	/** The Request id. */
	public static final String REQUEST_ID = "SessionId";

	/** The Request key. */
	public static final String REQUEST_KEY = "SessionKey";

	public static final String REQUEST_DATE_PATTEREN = "MM-dd-yyyy";

	public static final String REQUEST_TIME_PATTEREN = "HH:mm:ss";

    public static final String DEFAULT_ENCODING = "UTF-8";

	public static final Integer ZERO_INT = 0;
	public static final Integer ONE_INT = 1;
	public static final Integer TWO_INT = 2;

	public static final String DATE_PATTEREN_WITHOUT_UTC = "MM-dd-yyyy HH:mm:ss";

	public static final String DATE_PATTEREN = DATE_PATTEREN_WITHOUT_UTC + " SSS Z";

	public static final String US_DATE_PATTEREN = "MM/dd/yyyy";

	/* KeyCloak User Keys */
	public static final String KEYCLOAK_USER_ID = "UserId";
	public static final String KEYCLOAK_IS_EXPIRED = "IsExpired";
	public static final String KEYCLOAK_EMAIL_VERIFIED = "EmailVerified";
	public static final String KEYCLOAK_NAME = "Name";
	public static final String KEYCLOAK_USERNAME = "UserName";
	public static final String KEYCLOAK_EMAIL = "Email";
	public static final String KEYCLOAK_FIRSTNAME = "FirstName";
	public static final String KEYCLOAK_LASTNAME = "LastName";
	public static final String KEYCLOAK_ISSUER = "Issuer";
	public static final String KEYCLOAK_ROLES = "Roles";
	public static final String KEYCLOAK_ATTR_CLIENT = "Client";
	public static final String KEYCLOAK_ATTR_MERCHANT = "Merchant";
	public static final String KEYCLOAK_ATTR_Merchant_LOCATION = "MerchantLocation";

	/* KeyCloak Claims User Keys */
	public static final String KEYCLOAK_CLAIMS_EMAIL_VERIFIED = "email_verified";
	public static final String KEYCLOAK_CLAIMS_NAME = "name";
	public static final String KEYCLOAK_CLAIMS_USERNAME = "preferred_username";
	public static final String KEYCLOAK_CLAIMS_EMAIL = "email";
	public static final String KEYCLOAK_CLAIMS_FIRSTNAME = "given_name";
	public static final String KEYCLOAK_CLAIMS_LASTNAME = "family_name";
	public static final String KEYCLOAK_CLAIMS_REALM_ACCESS = "realm_access";
	public static final String KEYCLOAK_CLAIMS_ROLES = "roles";
	public static final String LOGOUT_SUCCESS = "Logout successfully";
	public static final String ACS_USER_TYPE = "ACS";
	public static final String CONNECT_USER_TYPE = "CONNECT";
	public static final String DEFAULT_PASSWORD = "28776102760608161e337c711a2006d6"; //Adv@tix@123

	public static String RESET_LINK_SENT;
	public static final String INVALID_CREDENTIALS = "Password must be minimum 8 and maximum 15 characters, at least one uppercase letter, one lowercase letter, one digit and one special character from allowed characters !,@,#,$";


	@Value("${password.reset.links.sent}")
	public void setPasswordResetLinkSentMessage(String successMessage) {
		RESET_LINK_SENT = successMessage;
	}

	public static String PASSWORD_RESET_SUCCESS;

	@Value("${password.reset.success.message}")
	public void setPasswordResetSuccessMessage(String successMessage) {
		PASSWORD_RESET_SUCCESS = successMessage;
	}

}
