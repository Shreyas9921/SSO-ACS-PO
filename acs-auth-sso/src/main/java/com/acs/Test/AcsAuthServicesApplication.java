package com.acs.Test;

import com.acs.common.logger.Logger;
import com.acs.common.utils.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
@ComponentScan("com.acs")
@EnableAsync
@PropertySource(ignoreResourceNotFound = true, value = {
		"file:${" + AcsAuthServicesApplication.PROPERTIES_LOCATION_ENV + "}/" + AcsAuthServicesApplication.APPLICATION_PROPERTY
				+ ".properties",
		"file:${" + AcsAuthServicesApplication.PROPERTIES_LOCATION_ENV + "}/" + AcsAuthServicesApplication.ERROR_MESSAGES_PROPERTY
				+ ".properties",
		"file:${" + AcsAuthServicesApplication.COMMON_PROPERTIES_LOCATION_ENV + "}/" + AcsAuthServicesApplication.COMMON_PROPERTY
				+ ".properties" })
//@EnableJpaRepositories
@EnableWebMvc
@EnableSpringDataWebSupport
public class AcsAuthServicesApplication {

	private static Logger log = Logger.getLogger(AcsAuthServicesApplication.class);

	public static final String PROPERTIES_LOCATION_ENV = "spring.config.location";

	public static final String COMMON_PROPERTIES_LOCATION_ENV = "spring.config.additional-location";
	public static final String COMMON_PROPERTY = "common";
	public static final String APPLICATION_PROPERTY = "acs-auth-test-application";
	public static final String ERROR_MESSAGES_PROPERTY = "acs-auth-test-error-messages";

	protected static final List<String> PROPERTY_FILES = Arrays.asList(APPLICATION_PROPERTY, ERROR_MESSAGES_PROPERTY, COMMON_PROPERTY);
	public static final String PROPERTIES_FILE_NAME = String.join(",", PROPERTY_FILES);

	public static void main(String[] args) {
		String configLocation = System.getProperty(AcsAuthServicesApplication.PROPERTIES_LOCATION_ENV, "classpath:/");
		//String commonConfigLocation = System.getProperty(AcsAuthServicesApplication.COMMON_PROPERTIES_LOCATION_ENV, "classpath:/");
		//log.info("Config-Paths: {} {}", configLocation, commonConfigLocation);
		if (StringUtils.isNotBlank(configLocation)) {
			Properties props = new Properties();
			//props.setProperty(AcsAuthServicesApplication.PROPERTIES_LOCATION_ENV, configLocation);
			//props.setProperty(AcsAuthServicesApplication.COMMON_PROPERTIES_LOCATION_ENV, commonConfigLocation);
			props.setProperty("spring.config.name", AcsAuthServicesApplication.PROPERTIES_FILE_NAME);
			ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AcsAuthServicesApplication.class)
					.properties(props).build().run(args);
			applicationContext.getEnvironment();
		} else {
			SpringApplication.run(AcsAuthServicesApplication.class, args);
		}
	}
}
