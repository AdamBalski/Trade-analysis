package com.trade_analysis.security;

import com.trade_analysis.dao.UserDbDao;
import com.trade_analysis.logs.Logger;
import com.trade_analysis.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.trade_analysis.model.UserRole.ADMIN;
import static com.trade_analysis.model.UserRole.USUAL;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {
    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    Logger logger;
    @Mock
    UserDbDao userDbDao;

    List<User> users;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

        users = of(
                new User(UUID.fromString("fd91c269-ab5c-4f8a-907a-e7f044239781"), "username3", "username3@email.com",
                                "$2y$10$CS.lGeJ7JyUQdkUl06Gt4uGb1jahebbYvc5EFYDT0BtZ.0uCbtGoy", USUAL, "X16ZGGPQPT13PIRE"),

                new User(UUID.fromString("48ee79d6-3350-4ab4-a33f-f176051741e4"), "username4", "username4@email.com",
                        "$2y$10$f0D5rrsIjmCfWLLcfi7XP.LERwZyjSfircn9tAj0NWestb.qR6FKS", USUAL, null),

                new User(UUID.fromString("1a0c1f7e-9b6d-44cd-80c2-bb166f29f082"), "username5", "username5@email.com",
                        "$2y$10$O3wX61NRvWFNaPYhB6xc4euQTzEqAVtl2YVJDFd9d3hB6Y7kWTDue", ADMIN, "3QP33URO6DL3OPTC"));
    }

    @Test
    void testLoadUserByUsername() {
        User user = users.get(0);
        String username = user.getUsername();

        when(userDbDao.getSingleResultByUsername(username)).thenReturn(Optional.of(user));

        org.springframework.security.core.userdetails.User expected =
                new org.springframework.security.core.userdetails.User(
                        "username3",
                        "$2y$10$CS.lGeJ7JyUQdkUl06Gt4uGb1jahebbYvc5EFYDT0BtZ.0uCbtGoy",
                        of(new GrantedAuthorityImpl(USUAL)));

        assertEquals(expected, userDetailsService.loadUserByUsername(username));
    }

    @Test
    void testLoadUserByUsernameWhenUserDoesNotExist() {
        User user = users.get(0);
        String username = user.getUsername();

        when(userDbDao.getSingleResultByUsername(username)).thenReturn(Optional.empty());

        Executable loadUserByUsernameExecutable = () -> userDetailsService.loadUserByUsername(username);
        assertThrows(UsernameNotFoundException.class, loadUserByUsernameExecutable);
    }
}