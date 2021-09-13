package com.epam.esm.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Class contains spring service configuration.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
@Configuration
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
public class ServiceConfiguration {

    /**
     * Creates beam ModelMapper for mapping entity to dto and opposite
     *
     * @return the model mapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true);
        return modelMapper;
    }

    /**
     * Creates bean TransactionManager for managing transactions
     *
     * @param dataSource the data source
     * @return the transaction manager
     */
    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
