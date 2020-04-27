package com.trade_analysis.security;

import com.trade_analysis.model.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;

import static com.trade_analysis.model.UserRole.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GrantedAuthorityImplTest {

    @Test
    void testGetAuthority() {
        String authority = new GrantedAuthorityImpl(USUAL).getAuthority();
        assertEquals("ROLE_USUAL", authority);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "ADMIN, ADMIN",
            "SUPER_ADMIN, USUAL"
    })
    void testEqualsIsSymmetric(UserRole userRole1, UserRole userRole2) {
        GrantedAuthorityImpl grantedAuthority1 = new GrantedAuthorityImpl(userRole1);
        GrantedAuthorityImpl grantedAuthority2 = new GrantedAuthorityImpl(userRole2);

        boolean equals1 = grantedAuthority1.equals(grantedAuthority2);
        boolean equals2 = grantedAuthority2.equals(grantedAuthority1);

        assertEquals(equals1, equals2);
    }

    @Test
    void testEqualsIsReflexive() {
        GrantedAuthorityImpl grantedAuthority = new GrantedAuthorityImpl(USUAL);

        assertEquals(grantedAuthority, grantedAuthority);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "USUAL, USUAL, SUPER_ADMIN",
            "USUAL, USUAL, USUAL"
    })
    void testEqualsIsTransitive(UserRole userRole1, UserRole userRole2, UserRole userRole3) {
        GrantedAuthorityImpl grantedAuthority1 = new GrantedAuthorityImpl(userRole1);
        GrantedAuthorityImpl grantedAuthority2 = new GrantedAuthorityImpl(userRole2);
        GrantedAuthorityImpl grantedAuthority3 = new GrantedAuthorityImpl(userRole3);

        boolean equals1 = grantedAuthority1.equals(grantedAuthority2);
        boolean equals2 = grantedAuthority1.equals(grantedAuthority3);

        if(equals1 && equals2) {
            assertEquals(grantedAuthority2, grantedAuthority3);
        }
        if(equals1 && !equals2) {
            assertNotEquals(grantedAuthority2, grantedAuthority3);
        }
    }

    @Test
    void testEqualsWithEqualGrantedAuthority() {
        assertEquals(new GrantedAuthorityImpl(USUAL), new GrantedAuthorityImpl(USUAL));
    }

    @Test
    void testEqualsWithNotEqualGrantedAuthority() {
        assertNotEquals(new GrantedAuthorityImpl(SUPER_ADMIN), new ArrayList<>());
    }

    @Test
    void testEqualsWithNull() {
        assertNotEquals(new GrantedAuthorityImpl(ADMIN), null);
    }

    @Test
    void testEqualsWithNotGrantedAuthorityImpl() {
        assertNotEquals(new GrantedAuthorityImpl(USUAL), new ArrayList<>());
    }
}