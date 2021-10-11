package com.epam.esm.entity;

/**
 * Class presents kinds of permissions.
 *
 * @author Maryia_Kuksar
 * @version 1.0
 */
public enum Permission {
    READ("read"),
    READ_ALL("read_all"),
    WRITE("write"),
    MAKE_ORDER("make_order");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
