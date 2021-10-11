package com.epam.esm.security;

import com.epam.esm.dto.JwtUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class AccessVerifier {

    public void checkAccess(long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        if (!jwtUser.isAdmin() && jwtUser.getId() != userId) {
            throw new AccessDeniedException("access is denied");
        }
    }
}
