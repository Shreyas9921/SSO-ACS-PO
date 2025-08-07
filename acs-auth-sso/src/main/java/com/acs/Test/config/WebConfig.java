/*
 * @author Advatix
 * 
 * @since 2019
 * 
 * @version 1.0
 */
package com.acs.Test.config;

import com.acs.Test.AcsAuthServicesApplication;
import com.acs.common.annotation.AuthenticatedArgumentResolver;
import com.acs.common.logger.Logger;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.jboss.resteasy.util.HttpHeaderNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * The Class WebConfig.
 */
@Configuration
// @EnableWebMvc
@Priority(value = 1)
public class WebConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {
	
	// en,fr,hi
	@Value("${locales}:en")
	private String[] locales;
	
	@Value("${allowed.domains}")
	private String[] allowedDomains;

//	@Autowired
//	private LoggingInterceptor loggingInterceptor;

	private static final Logger log = Logger.getLogger(WebConfig.class);

	/**
	 * Velocity engine.
	 *
	 * @return the velocity engine
	 */


	/**
	 * Cors filter.
	 *
	 * @return the filter registration bean
	 */
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(false);
		for (String domain: allowedDomains) {
			config.addAllowedOrigin(domain);
		}
//		config.addAllowedOrigin(CorsConfiguration.ALL);
		config.addAllowedHeader(CorsConfiguration.ALL);
		config.addAllowedMethod(CorsConfiguration.ALL);
		config.addExposedHeader("Authorization");
		config.addExposedHeader("Content-Type");
		config.addExposedHeader("X-AUTH-TOKEN");
		config.addExposedHeader("AUTH-TOKEN");
		config.addExposedHeader("Device-Type");
		config.addExposedHeader("AppVersionNo");
		config.setMaxAge(1L);
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

	/**
	 * Adds the resource handlers.
	 *
	 * @param registry the registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * Configure message converters.
	 *
	 * @param converters the converters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.serializationInclusion(Include.NON_EMPTY);
		converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
	}

	@Bean(name = "processExecutor")
	public TaskExecutor workExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setThreadNamePrefix("Xpdel-Async-");
		threadPoolTaskExecutor.setCorePoolSize(10);
		threadPoolTaskExecutor.setMaxPoolSize(Integer.MAX_VALUE);
		threadPoolTaskExecutor.setQueueCapacity(Integer.MAX_VALUE);
		threadPoolTaskExecutor.afterPropertiesSet();
		return threadPoolTaskExecutor;
	}

	@Bean(name = "processExecutorInventoryLogs")
	public TaskExecutor workExecutorInventoryLogs() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setThreadNamePrefix("Xpdel-Inv-Logs-");
		threadPoolTaskExecutor.setCorePoolSize(10);
		threadPoolTaskExecutor.setMaxPoolSize(Integer.MAX_VALUE);
		threadPoolTaskExecutor.setQueueCapacity(Integer.MAX_VALUE);
		threadPoolTaskExecutor.afterPropertiesSet();
		return threadPoolTaskExecutor;
	}

	@Bean
	public AuthenticatedArgumentResolver authenticatedArgumentResolver() {
		return new AuthenticatedArgumentResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(authenticatedArgumentResolver());
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		String configLocation = System.getProperty(AcsAuthServicesApplication.PROPERTIES_LOCATION_ENV, "classpath:");
		String configPath = configLocation + AcsAuthServicesApplication.ERROR_MESSAGES_PROPERTY;
		log.info(configPath);
		messageSource.setBasename(configPath);
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		messageSource.setUseCodeAsDefaultMessage(Boolean.TRUE);
		return messageSource;
	}

	@Bean
	@Override
	public Validator getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String headerLang = request.getHeader(HttpHeaderNames.ACCEPT_LANGUAGE);
		return headerLang == null || headerLang.isEmpty() ? Locale.getDefault()
				: new Locale(Locale.lookupTag(Locale.LanguageRange.parse(headerLang), Arrays.asList(locales)));
	}

}
