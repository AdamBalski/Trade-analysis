package com.trade_analysis.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserSignUpDtoTest {

    @Test
    void testEmpty() {
        UserSignUpDto empty = UserSignUpDto.empty();

        assertEquals("", empty.getUsername());
        assertEquals("", empty.getEmail());
        assertEquals("", empty.getPassword1());
        assertEquals("", empty.getPassword2());
    }

    @Test
    void testBuilder() {
        UserSignUpDto built = UserSignUpDto.builder()
                .username("USERNAME")
                .email("email@email.com")
                .password1("password1")
                .password2("password2")
                .build();

        assertEquals("USERNAME", built.getUsername());
        assertEquals("email@email.com", built.getEmail());
        assertEquals("password1", built.getPassword1());
        assertEquals("password2", built.getPassword2());
    }
}