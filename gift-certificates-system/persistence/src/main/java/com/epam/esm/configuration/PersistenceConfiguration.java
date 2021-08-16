package com.epam.esm.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan("com.epam.esm")
public class PersistenceConfiguration {
    @Value("${db.driverClassName}")
    private String driverClassName;
    @Value("${db.userName}")
    private String userName;
    @Value("${db.password}")
    private String password;
    @Value("${db.jdbcUrl}")
    private String jdbcUrl;

    @Bean
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setUsername(userName);
        hikariDataSource.setPassword(password);
        hikariDataSource.setJdbcUrl(jdbcUrl);
        return hikariDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
