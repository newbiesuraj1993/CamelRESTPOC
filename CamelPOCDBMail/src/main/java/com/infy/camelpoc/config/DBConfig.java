package com.infy.camelpoc.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DBConfig {
	@Bean(name="dataSource")
	@ConfigurationProperties(prefix="spring.datasource")
	@Primary
	public DataSource dataSource() {
		
		    DriverManagerDataSource dataSource = new DriverManagerDataSource();
		    dataSource.setDriverClassName("org.postgresql.Driver");
		    dataSource.setUrl("jdbc:postgresql://127.0.0.1:5432/postgres");
		    dataSource.setUsername("postgres");
		    dataSource.setPassword("root");
		 
		    
		    return dataSource;
	}
}
