package com.acs.Test.dto;

import lombok.Data;

@Data
public class ValidateApiKeyResponseDto {
	
	boolean responseStatus;
	
	int responseStatusCode;
	
	Object responseObject;
	

}
