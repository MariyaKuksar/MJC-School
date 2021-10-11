package com.epam.esm.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter for token validation.
 *
 * @author Maryia_Kuksar
 * @version 1.0
 * @see OncePerRequestFilter
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final String GIFT_CERTIFICATES_ENDPOINT = "/gift-certificates";
    private static final String AUTH_ENDPOINT = "/auth";
    private static final String METHOD_GET = "GET";
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        if (!isAvailableWithoutToken(httpServletRequest) && StringUtils.isNotBlank(token)) {
            jwtTokenProvider.validateToken(token);
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean isAvailableWithoutToken(HttpServletRequest httpServletRequest) {
        return (httpServletRequest.getRequestURI().contains(GIFT_CERTIFICATES_ENDPOINT) && httpServletRequest.getMethod().equals(METHOD_GET))
                || httpServletRequest.getRequestURI().contains(AUTH_ENDPOINT);
    }
}
