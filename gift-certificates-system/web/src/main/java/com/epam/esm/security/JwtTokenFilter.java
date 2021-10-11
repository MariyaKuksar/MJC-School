package com.epam.esm.security;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.MessageKey;
import com.epam.esm.exception.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LogManager.getLogger();
    private static final String GIFT_CERTIFICATES_ENDPOINT = "/gift-certificates";
    private static final String AUTH_ENDPOINT = "/auth";
    private static final String METHOD_GET = "GET";
    private static final String ENCODING = "UTF-8";
    private final JwtTokenProvider jwtTokenProvider;
    private final MessageSource messageSource;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, MessageSource messageSource) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.messageSource = messageSource;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (isAvailableWithoutToken(httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        try {
            if (StringUtils.isBlank(token)) {
                throw new JwtException("invalid token");
            }
            jwtTokenProvider.validateToken(token);
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (JwtException | UsernameNotFoundException exception) {
            SecurityContextHolder.clearContext();
            sendError(httpServletRequest, httpServletResponse);
            logger.error(HttpStatus.UNAUTHORIZED, exception);
        }
    }

    private void sendError(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String errorMessage = messageSource.getMessage(MessageKey.NO_RIGHTS, new String[]{}, httpServletRequest.getLocale());
        httpServletResponse.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(ENCODING);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter()
                .write(String.valueOf(new ObjectMapper()
                        .writeValueAsString(new ResponseMessage(errorMessage,
                                HttpStatus.UNAUTHORIZED.value() + ErrorCode.DEFAULT.getErrorCode()))));
    }

    private boolean isAvailableWithoutToken(HttpServletRequest httpServletRequest) {
        return (httpServletRequest.getRequestURI().contains(GIFT_CERTIFICATES_ENDPOINT) && httpServletRequest.getMethod().equals(METHOD_GET))
                || httpServletRequest.getRequestURI().contains(AUTH_ENDPOINT);
    }
}
