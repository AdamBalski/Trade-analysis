package com.trade_analysis.security;

import com.trade_analysis.model.UserRole;
import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImpl implements GrantedAuthority {
    private final UserRole userRole;

    public GrantedAuthorityImpl(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override public String getAuthority() {
        return "ROLE_" + userRole.name();
    }
}
