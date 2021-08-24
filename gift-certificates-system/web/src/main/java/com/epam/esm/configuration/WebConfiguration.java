package com.epam.esm.configuration;

import com.epam.esm.converter.StringToSortingOrderConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
public class WebConfiguration implements WebMvcConfigurer {
    @Value("message")
    private String baseName;
    @Value("UTF-8")
    private String encoding;
    @Value("ru")
    private String languageRu;
    @Value("en")
    private String languageEn;
    private final StringToSortingOrderConverter stringToSortingOrderConverter;

    @Autowired
    public WebConfiguration(StringToSortingOrderConverter stringToSortingOrderConverter) {
        this.stringToSortingOrderConverter = stringToSortingOrderConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToSortingOrderConverter);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(baseName);
        messageSource.setDefaultEncoding(encoding);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Profile("dev")
    @Bean (name="localeResolver")
    public LocaleResolver devLocaleResolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(new Locale(languageRu));
        return acceptHeaderLocaleResolver;
    }

    @Profile("prod")
    @Bean (name="localeResolver")
    public LocaleResolver prodLocaleResolver(){
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(new Locale(languageEn));
        return acceptHeaderLocaleResolver;
    }

}
