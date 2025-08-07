package com.acs.Test.commons.utils;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * The Class RestResponse.
 *
 * @param <T> the generic type
 */

@Data
@Component
public class OrdCreationRestResponse {

	/** The response message. */
	private String responseMessage;

	/** The response status. */
	private Boolean responseStatus = true;

	/** The response status code. */
	private Integer responseStatusCode = 200;

	/** The response object. */
	private String responseObject;

	/** The response orderNumber. */
	private String orderNumber;

}
