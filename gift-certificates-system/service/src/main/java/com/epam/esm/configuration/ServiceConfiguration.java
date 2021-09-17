package com.epam.esm.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Class contains spring service configuration.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
@SpringBootApplication (scanBasePackages = "com.epam.esm")
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
}
