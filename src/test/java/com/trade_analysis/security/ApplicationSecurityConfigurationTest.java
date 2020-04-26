package com.trade_analysis.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationSecurityConfigurationTest {

    @Test
    void testGetUserDetailsServiceBean() {
        UserDetailsService userDetailsService = new ApplicationSecurityConfiguration().userDetailsService();
        assertTrue(userDetailsService instanceof UserDetailsServiceImpl);
    }
}