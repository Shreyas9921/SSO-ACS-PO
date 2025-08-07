package com.acs.Test.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class PodatasourceConfig {

    //@Primary
    @Bean
    @ConfigurationProperties("spring.datasource.po")
    public DataSourceProperties poMySqlProperties() {
        return new DataSourceProperties();
    }

    //@Primary
    @Bean(name = "poDataSource")
    // @ConfigurationProperties(prefix = "spring.datasource.po")
    public DataSource poDataSource() {
        return poMySqlProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
}
