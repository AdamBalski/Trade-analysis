package com.trade_analysis.model;

import com.trade_analysis.dtos.UserSignUpDto;
import com.trade_analysis.security.GrantedAuthorityImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.UUID;

import static com.trade_analysis.model.UserRole.ADMIN;
import static com.trade_analysis.model.UserRole.USUAL;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    // Users to index 2 (inclusively) are unique, but users from index 2 (inclusively) are equal to each other
    private List<User> users;

    @BeforeEach
    public void init() {
        users = List.of(
                new User(UUID.fromString("fd91c269-ab5c-4f8a-907a-e7f044239781"), "username3", "username3@email.com",
                        "$2y$10$CS.lGeJ7JyUQdkUl06Gt4uGb1jahebbYvc5EFYDT0BtZ.0uCbtGoy", USUAL),

                new User(UUID.fromString("48ee79d6-3350-4ab4-a33f-f176051741e4"), "username4", "username4@email.com",
                        "$2y$10$f0D5rrsIjmCfWLLcfi7XP.LERwZyjSfircn9tAj0NWestb.qR6FKS", USUAL),

                new User(UUID.fromString("1a0c1f7e-9b6d-44cd-80c2-bb166f29f082"), "username5", "username5@email.com",
                        "$2y$10$O3wX61NRvWFNaPYhB6xc4euQTzEqAVtl2YVJDFd9d3hB6Y7kWTDue", ADMIN),

                new User(UUID.fromString("1a0c1f7e-9b6d-44cd-80c2-bb166f29f082"), "username5", "username5@email.com",
                        "$2y$10$O3wX61NRvWFNaPYhB6xc4euQTzEqAVtl2YVJDFd9d3hB6Y7kWTDue", ADMIN),

                new User(UUID.fromString("1a0c1f7e-9b6d-44cd-80c2-bb166f29f082"), "username5", "username5@email.com",
                        "$2y$10$O3wX61NRvWFNaPYhB6xc4euQTzEqAVtl2YVJDFd9d3hB6Y7kWTDue", ADMIN)
        );
    }

    @Test
    public void should_return_valid_to_string() {
        String expected = "User{id='1a0c1f7e-9b6d-44cd-80c2-bb166f29f082',username='username5',email='username5@email.com'," +
                "password='$2y$10$O3wX61NRvWFNaPYhB6xc4euQTzEqAVtl2YVJDFd9d3hB6Y7kWTDue',userRole='ADMIN'}";

        assertEquals(expected, users.get(2).toString());
    }

    @Test
    public void should_return_link() {
        assertEquals("<a href = '/user/1a0c1f7e-9b6d-44cd-80c2-bb166f29f082' >User{id='1a0c1f7e-9b6d-44cd-80c2-bb166f29f082'," +
                "username='username5',email='username5@email.com'," +
                "password='$2y$10$O3wX61NRvWFNaPYhB6xc4euQTzEqAVtl2YVJDFd9d3hB6Y7kWTDue',userRole='ADMIN'}</a>",
                users.get(2).getLink());
    }

    @Test
    public void should_return_granted_authorities() {
        assertEquals(List.of(new GrantedAuthorityImpl(USUAL)), users.get(0).getGrantedAuthorities());
    }

    @Test
    public void should_return_value_of_dto() {
        User user = User.valueOf(UserSignUpDto
                .builder()
                .username("username")
                .email("email@email.email")
                .password1("password")
                .password2("password")
                .build());

        assertEquals("username", user.getUsername());
        assertEquals("email@email.email", user.getEmail());
        assertTrue(BCrypt.checkpw("password", user.getPassword()));
        assertEquals(USUAL, user.getUserRole());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0, 1",
            "0, 0",
            "3, 4"
    })
    public void should_return_the_same_hash_code_if_they_are_equals(int a, int b) {
        User first = users.get(a);
        User second = users.get(b);

        int firstHashCode = first.hashCode();
        int secondHashCode = second.hashCode();
        boolean equals = first.equals(second);

        if(equals) {
            assertEquals(firstHashCode, secondHashCode);
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0, 0",
            "0, 1",
            "3, 4"
    })
    public void should_return_true_equals_and_the_same_hashcode_if_they_are_the_same(int a, int b) {
        User first = users.get(a);
        User second = users.get(b);

        int firstHashCode = first.hashCode();
        int secondHashCode = second.hashCode();
        boolean equals = first.equals(second);

        if(a == b || a + b >= 4) {
            assertEquals(firstHashCode, secondHashCode);
        }
        else {
            assertFalse(equals);
        }

    }

    @ParameterizedTest
    @CsvSource(value = {
            "0, 0, 0",
            "2, 3, 4",
            "2, 3, 0",
            "0, 1, 2"
    })
    public void should_return_transitive_equals(int a, int b, int c) {
        User first = users.get(a);
        User second = users.get(b);
        User third = users.get(c);

        if(first.equals(second) && first.equals(third)) {
            assertEquals(second, third);
        }
        else if(first.equals(second) && !second.equals(third)) {
            assertNotEquals(first, third);
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0, 1",
            "3, 4"
    })
    public void should_return_symmetric_equals(int a, int b) {
        User first = users.get(a);
        User second = users.get(b);

        assertEquals(first.equals(second), second.equals(first));
    }

    @Test
    public void should_return_reflexive_equals() {
        assertEquals(users.get(0), users.get(0));
    }

    @Test
    public void should_return_false_when_passing_null_to_equals() {
        assertFalse(users.get(0).equals(null));
    }

    @Test
    public void should_return_false_when_passing_object_of_another_class_to_equals() {
        assertFalse(users.get(0).equals(new Object()));
    }
}