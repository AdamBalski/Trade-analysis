package com.trade_analysis.service;

import com.trade_analysis.dao.UserDbDao;
import com.trade_analysis.dtos.UserSignUpDto;
import com.trade_analysis.exception.UserNotFoundException;
import com.trade_analysis.logs.Logger;
import com.trade_analysis.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.NonUniqueResultException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.trade_analysis.model.UserRole.ADMIN;
import static com.trade_analysis.model.UserRole.USUAL;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    Logger logger;
    @Mock
    UserDbDao userDbDao;

    private List<User> users;
    private UserSignUpDto userSignUpDto;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

        users = of(
                new User(UUID.fromString("fd91c269-ab5c-4f8a-907a-e7f044239781"), "username3", "username3@email.com",
                        "$2y$10$CS.lGeJ7JyUQdkUl06Gt4uGb1jahebbYvc5EFYDT0BtZ.0uCbtGoy", USUAL),

                new User(UUID.fromString("48ee79d6-3350-4ab4-a33f-f176051741e4"), "username4", "username4@email.com",
                        "$2y$10$f0D5rrsIjmCfWLLcfi7XP.LERwZyjSfircn9tAj0NWestb.qR6FKS", USUAL),

                new User(UUID.fromString("1a0c1f7e-9b6d-44cd-80c2-bb166f29f082"), "username5", "username5@email.com",
                        "$2y$10$O3wX61NRvWFNaPYhB6xc4euQTzEqAVtl2YVJDFd9d3hB6Y7kWTDue", ADMIN));

        userSignUpDto = new UserSignUpDto("username", "email@email.com", "password1", "password2");
    }

    @Test
    void testGetUserByUsername() {
        User user = users.get(0);
        String username = user.getUsername();

        when(userDbDao.getSingleResultByUsername(username)).thenReturn(Optional.of(user));

        try {
            assertEquals(user, userService.getUserByUsername(username));
        } catch (UserNotFoundException e) {
            fail("testGetUserByUsername failed. UserNotFoundException was thrown although mock object returns 'full' optional.");
        }

        verify(logger, never()).save(any(Class.class), any(Exception.class));
    }

    @Test
    void testGetUserByUsernameWhenOccursMoreThanOnce() {
        User user = users.get(0);
        String username = user.getUsername();

        when(userDbDao.getSingleResultByUsername(username)).thenThrow(new NonUniqueResultException(""));

        Executable executable = () -> userService.getUserByUsername(username);
        assertThrows(UsernameNotFoundException.class, executable);

        verify(logger).save(any(Class.class), any(Exception.class));
    }

    @Test
    void testFindUserByUsernameWhenUserDoesNotExist() {
        User user = users.get(0);
        String username = user.getUsername();

        when(userDbDao.getSingleResultByUsername(username)).thenReturn(Optional.empty());

        Executable executable = () -> userService.getUserByUsername(username);
        assertThrows(UserNotFoundException.class, executable);

        verify(logger, never()).save(any(Class.class), any(Exception.class));
    }

    @Test
    void testGetAllUsersWhenThereIsNoUser() {
        assertEquals(of(), userService.getAllUsers());
    }

    @Test
    void testGetAllUsers() {
        when(userDbDao.findAll()).thenReturn(users);

        assertEquals(users, userService.getAllUsers());
    }

    @Test
    void testGetAllUserLinksWhenThereIsNoUser() {
        assertEquals(of(), userService.getAllUserLinks());
    }

    @Test
    void testGetAllUserLinks() {
        List<User> userList = of(users.get(1), users.get(2));
        when(userDbDao.findAll()).thenReturn(userList);

        List<String> expected = of(
                "<a href = '/user/48ee79d6-3350-4ab4-a33f-f176051741e4' >User{id='48ee79d6-3350-4ab4-a33f-f176051741e4'," +
                    "username='username4',email='username4@email.com'," +
                    "password='$2y$10$f0D5rrsIjmCfWLLcfi7XP.LERwZyjSfircn9tAj0NWestb.qR6FKS',userRole='USUAL'}</a>",
                "<a href = '/user/1a0c1f7e-9b6d-44cd-80c2-bb166f29f082' >User{id='1a0c1f7e-9b6d-44cd-80c2-bb166f29f082'," +
                        "username='username5',email='username5@email.com'," +
                        "password='$2y$10$O3wX61NRvWFNaPYhB6xc4euQTzEqAVtl2YVJDFd9d3hB6Y7kWTDue',userRole='ADMIN'}</a>");

        assertEquals(expected, userService.getAllUserLinks());
    }

    @Test
    void testFindUserById() {
        User user = users.get(0);
        UUID id = user.getId();

        when(userDbDao.getSingleResultById(id)).thenReturn(Optional.of(user));

        try {
            assertEquals(user, userService.findUserById(id));
        } catch (UserNotFoundException e) {
            fail("testFindUserById failed. UserNotFoundException was thrown although mock object returns 'full' optional.");
        }
    }

    @Test
    void testFindUserByIdWhenOccursMoreThanOnce() {
        User user = users.get(0);
        UUID id = user.getId();

        when(userDbDao.getSingleResultById(id)).thenThrow(new NonUniqueResultException());

        Executable executable = () -> userService.findUserById(id);
        assertThrows(NonUniqueResultException.class, executable);
    }

    @Test
    void testFindUserByIdWhenUserDoesNotExist() {
        User user = users.get(0);
        UUID id = user.getId();

        when(userDbDao.getSingleResultById(id)).thenReturn(Optional.empty());

        Executable executable = () -> userService.findUserById(id);
        assertThrows(UserNotFoundException.class, executable);
    }

    @Test
    void testSignUp() {
        userService.signUp(userSignUpDto);
        verify(userDbDao).save(any(User.class));
    }

    @Test
    void testSignUpWhenPassingDuplicate() {
        when(userDbDao.save(any(User.class))).thenThrow(new DataIntegrityViolationException(""));

        Executable signUpExecutable = () -> {
            userService.signUp(userSignUpDto);
        };

        assertThrows(DataIntegrityViolationException.class, signUpExecutable);
    }

    @Test
    void testIsSomeoneWithThatUsernameWhenThereIs() {
        when(userDbDao.existsByUsername("email@email.email")).thenReturn(true);

        assertTrue(userService.existsByUsername("email@email.email"));
    }

    @Test
    void testIsSomeoneWithThatUsernameWhenThereIsNot() {
        when(userDbDao.existsByUsername("email@email.email")).thenReturn(false);

        assertFalse(userService.existsByUsername("email@email.email"));
    }

    @Test
    void testIsSomeoneWithThatEmailWhenThereIs() {
        when(userDbDao.existsByEmail("email@email.email")).thenReturn(true);

        assertTrue(userService.existsByEmail("email@email.email"));
    }

    @Test
    void testIsSomeoneWithThatEmailWhenThereIsNot() {
        when(userDbDao.existsByEmail("email@email.email")).thenReturn(false);

        assertFalse(userService.existsByEmail("email@email.email"));
    }

    @Test
    void testDeletingUser() {
        UUID id = UUID.randomUUID();

        userService.deleteUserById(id);

        verify(userDbDao).deleteById(id);
    }
}