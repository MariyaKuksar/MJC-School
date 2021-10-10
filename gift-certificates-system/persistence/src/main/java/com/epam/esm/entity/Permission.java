package com.epam.esm.entity;

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
