package com.epam.esm.security;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.MessageKey;
import com.epam.esm.exception.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class is implementation of interface {@link AuthenticationEntryPoint}
 * for customize authentication process.
 *
 * @author Maryia_Kuksar
 * @version 1.0
 * @see AuthenticationEntryPoint
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LogManager.getLogger();
    private static final String ENCODING = "UTF-8";
    private final MessageSource messageSource;

    @Autowired
    public CustomAuthenticationEntryPoint(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authException) throws IOException {
        String errorMessage = messageSource.getMessage(MessageKey.NO_RIGHTS, new String[]{}, httpServletRequest.getLocale());
        httpServletResponse.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(ENCODING);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter()
                .write(String.valueOf(new ObjectMapper()
                        .writeValueAsString(new ResponseMessage(errorMessage,
                                HttpStatus.UNAUTHORIZED.value() + ErrorCode.DEFAULT.getErrorCode()))));
        logger.error(HttpStatus.UNAUTHORIZED, authException);
    }
}