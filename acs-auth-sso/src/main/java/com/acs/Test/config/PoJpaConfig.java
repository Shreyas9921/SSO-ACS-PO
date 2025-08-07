package com.acs.Test.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackages = "com.acs.Test.repository",
//        entityManagerFactoryRef = "poEntityManagerFactory",
//        transactionManagerRef = "poTransactionManager"
//)
public class PoJpaConfig {

    /*private final Environment env;

    public PoJpaConfig(Environment env) {
        this.env = env;
    }*/

/*@Primary
    @Bean(name = "poEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean poEntityManagerFactory*/

    //@Primary
    @Bean(name = "poEntityManagerFactory")
    @DependsOn("poDataSource")
    public LocalContainerEntityManagerFactoryBean poEntityManagerFactory(
            @Qualifier("poDataSource") DataSource dataSource) {


        LocalContainerEntityManagerFactoryBean builder = new LocalContainerEntityManagerFactoryBean();
        builder.setDataSource(dataSource);
        builder.setPackagesToScan(new String[]{"com.acs.Test.pojo"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        builder.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
//         jpaProperties.put("hibernate.hbm2ddl.auto", "none");

        builder.setJpaPropertyMap(jpaProperties);
//        jpaProperties.put("hibernate.dialect", env.getProperty("spring.datasource.po.hibernate.dialect"));
//        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.datasource.po.hibernate.ddl-auto"));


        return builder;
        /*return builder
                .dataSource(dataSource)
                .packages("com.acs.Test.pojo") // Your PO entities
                .persistenceUnit("poPU")
                .properties(jpaProperties)
                .build();*/
    }

/*@Primary
    @Bean(name = "poTransactionManager")
    public PlatformTransactionManager poTransactionManager*/

    //@Primary
    @Bean(name = "poTransactionManager")
    @DependsOn("poEntityManagerFactory")
    //@DependsOn("poDataSource")
    public PlatformTransactionManager poTransactionManager(
            @Qualifier("poEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
