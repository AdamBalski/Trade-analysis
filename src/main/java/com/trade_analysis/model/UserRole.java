package com.trade_analysis.model;

import com.trade_analysis.security.GrantedAuthorityImpl;
import org.springframework.security.core.GrantedAuthority;

public enum UserRole {
    USUAL("USUAL"),
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN");

    String string;

    UserRole(String string) {
        this.string = string;
    }

    public GrantedAuthority getGrantedAuthority() {
        return new GrantedAuthorityImpl(this);
    }
}
