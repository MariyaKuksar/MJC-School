package com.epam.esm.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class presents user roles with authorities.
 *
 * @author Maryia_Kuksar
 * @version 1.0
 */
public enum Role {
    ADMIN(Set.of(Permission.READ, Permission.READ_ALL, Permission.WRITE, Permission.MAKE_ORDER)),
    USER(Set.of(Permission.READ, Permission.MAKE_ORDER));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
