package com.acs.Test.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource; // Assuming you have a DataSource bean defined
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
// @EnableJdbcHttpSession
public class JpaConfig {

    // @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.auth")
    public DataSourceProperties authMariadbProperties() {
        return new DataSourceProperties();
    }

    // @Primary
    @Bean(name = "authDataSource")
    public DataSource authDataSource() {
        return authMariadbProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    // @Primary
    // @Bean(name = "entityManagerFactory")
    @Bean(name = "authEntityManagerFactory")
    @DependsOn("authDataSource")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("authDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(new String[]{"com.acs.Test.pojo"}); // Replace with your entity package
        // You can also set other properties like JPA vendor adapter, etc.

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MariaDB103Dialect");

        em.setJpaPropertyMap(jpaProperties);

        return em;
    }

    // @Primary
    // @Bean(name = "transactionManager")
    // @DependsOn("entityManagerFactory")
    @Bean(name = "authTransactionManager")
    @DependsOn("authEntityManagerFactory")
    //@DependsOn("authDataSource")
    public PlatformTransactionManager transactionManager(
            /*@Qualifier("entityManagerFactory")*/ @Qualifier("authEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "authJdbcTemplate")
    @DependsOn("authDataSource")
    public JdbcTemplate authJdbcTemplate(
            @Qualifier("authDataSource") DataSource authDataSource) {
        return new JdbcTemplate(authDataSource);
    }

    @Bean(name = "authTransactionTemplate")
    @DependsOn("authTransactionManager")
    public TransactionTemplate authTransactionTemplate(
            @Qualifier("authTransactionManager") PlatformTransactionManager authTransactionManager) {
        return new TransactionTemplate(authTransactionManager);
    }

    @Bean(name = "authSessionRepository")
    public JdbcIndexedSessionRepository authSessionRepository(
            @Qualifier("authJdbcTemplate") JdbcTemplate authJdbcTemplate,
            @Qualifier("authTransactionTemplate") TransactionTemplate authTransactionTemplate) {

        JdbcIndexedSessionRepository repo =
                new JdbcIndexedSessionRepository(authJdbcTemplate, authTransactionTemplate);
        repo.setTableName("SPRING_SESSION"); // Ensure table exists in MariaDB
        return repo;
    }
}