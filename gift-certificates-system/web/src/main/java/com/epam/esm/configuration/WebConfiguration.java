package com.epam.esm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * Class contains spring web configuration.
 *
 * @author Maryia Kuksar
 * @version 1.0
 * @see WebMvcConfigurer
 */
@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class WebConfiguration implements WebMvcConfigurer {
    @Value("message")
    private String baseName;
    @Value("UTF-8")
    private String encoding;
    @Value("ru")
    private String languageRu;
    @Value("en")
    private String languageEn;
    private final Converter stringToSortingOrderConverter;

    @Autowired
    public WebConfiguration(Converter stringToSortingOrderConverter) {
        this.stringToSortingOrderConverter = stringToSortingOrderConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToSortingOrderConverter);
    }

    /**
     * Creates bean MessageSource for resolving messages, with support for the parameterization
     * and internationalization of such messages
     *
     * @return the message source
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(baseName);
        messageSource.setDefaultEncoding(encoding);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    /**
     * Creates bean LocaleResolver and set default locale for dev profile
     *
     * @return the local resolver
     */
    @Bean(name = "localeResolver")
    @Profile("dev")
    public LocaleResolver devLocaleResolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(new Locale(languageRu));
        return acceptHeaderLocaleResolver;
    }

    /**
     * Creates bean LocaleResolver and set default locale fo prod profile
     *
     * @return the local resolver
     */
    @Bean(name = "localeResolver")
    @Profile("prod")
    public LocaleResolver prodLocaleResolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(new Locale(languageEn));
        return acceptHeaderLocaleResolver;
    }

}
