package com.weverse.springoauth2practice.security;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
    ADMIN(ROLES.ADMIN), USER(ROLES.USER), MANAGER(ROLES.MANAGER);

    public static class ROLES {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String USER = "ROLE_USER";
        public static final String MANAGER = "ROLE_MANAGER";
    }

    UserAuthority(String userRole) {
        this.userRole = userRole;
    }

    private final String userRole;

    @Override
    public String getAuthority() {
        return this.userRole;
    }
}
