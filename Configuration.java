package com.lab2.students.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
@EnableJpaRepositories(basePackages = {"com.lab2.students.controller",
"com.lab2.students.repository",
"com.lab2.students.model",
"com.lab2.students",
"com.lab2.students.exception"})
@EnableTransactionManagement
public class Configuration {

    @Bean
    @Profile("test")
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://@postgres.url@:5433/students");
        dataSource.setUsername("postgres");
        dataSource.setPassword("28072001");

        return dataSource;
    }
}
