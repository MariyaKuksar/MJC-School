package com.epam.esm.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Class contains spring persistence configuration.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
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

    /**
     * Creates bean DataSource for working with database
     *
     * @return the data source
     */
    @Bean
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setUsername(userName);
        hikariDataSource.setPassword(password);
        hikariDataSource.setJdbcUrl(jdbcUrl);
        return hikariDataSource;
    }

    /**
     * Creates bean JdbcTemplate for executing queries to database
     *
     * @param dataSource the data source
     * @return the jdbc template
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
