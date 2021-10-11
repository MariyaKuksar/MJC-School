package com.epam.esm.security;

import com.epam.esm.dto.JwtUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 * Class is used to check the ability to access.
 *
 * @author Maryia_Kuksar
 * @version 1.0
 */
@Component
public class AccessVerifier {

    /**
     * Checks access
     *
     * @param userId the user id
     */
    public void checkAccess(long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        if (!jwtUser.isAdmin() && jwtUser.getId() != userId) {
            throw new AccessDeniedException("access is denied");
        }
    }
}
