package com.epam.esm.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Class is deployment descriptor. ApplicationInitializer to register a DispatcherServlet and use Java-based Spring
 * configuration
 *
 * @author Maryia Kuksar
 * @version 1.0
 * @see AbstractAnnotationConfigDispatcherServletInitializer
 */
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private static final String ACTIVE_PROFILE_PARAM = "spring.profiles.active";
    private static final String PROD_PROFILE = "prod";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setInitParameter(ACTIVE_PROFILE_PARAM, PROD_PROFILE);
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ServiceConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
