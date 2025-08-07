package com.acs.Test.utils;

import java.io.File;
import java.io.IOException;

import com.acs.Test.commons.logger.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperUtil {

	private ObjectMapperUtil() {
	}

	private static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	public static ObjectMapper getObjectMapper() {
		return mapper;
	}

	public static <T> T readValue(String json, TypeReference<T> valueTypeRef) throws IOException {
		return mapper.readValue(json, valueTypeRef);
	}

	public static <T> T readValue(String json, Class<T> type) throws IOException {
		return mapper.readValue(json, type);
	}

	public static <T> T readValue(File file, Class<T> type) throws IOException {
		return mapper.readValue(file, type);
	}

	public static <T> T readValue(File file, TypeReference<T> valueTypeRef) throws IOException {
		return mapper.readValue(file, valueTypeRef);
	}

	public static String writeValueAsString(Object obj, Logger log) {
		String json = "";
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error("Json Exception", e);
		}
		return json;
	}

}