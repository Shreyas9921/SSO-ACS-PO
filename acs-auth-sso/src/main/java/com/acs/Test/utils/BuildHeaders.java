package com.acs.Test.utils;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Configuration
public class BuildHeaders {

	public static HttpHeaders getCustomHeaders(Object webhookDetails) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-Api-Token", webhookDetails.toString());
		return headers;
	}

}
