package com.epam.esm.configuration;

import com.epam.esm.converter.StringToSortingOrderConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
public class WebConfiguration implements WebMvcConfigurer {
    @Value("message")
    private String baseName;
    @Value("UTF-8")
    private String encoding;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSortingOrderConverter());
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(baseName);
        messageSource.setDefaultEncoding(encoding);
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
}
