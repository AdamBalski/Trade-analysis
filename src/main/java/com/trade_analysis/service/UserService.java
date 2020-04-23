package com.trade_analysis.service;

import com.trade_analysis.dao.ExceptionDao;
import com.trade_analysis.dao.UserDbDao;
import com.trade_analysis.dtos.UserSignUpDto;
import com.trade_analysis.exception.UserNotFoundException;
import com.trade_analysis.exception.UsernameNotUniqueException;
import com.trade_analysis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.NonUniqueResultException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class UserService {
    @Autowired
    @Qualifier("exceptionDbDao")
    ExceptionDao exceptionDao;

    @Autowired
    @Qualifier("userDbDao")
    UserDbDao userDbDao;

    public User getUserByUsername(String username) throws UserNotFoundException, UsernameNotUniqueException {
        Optional<User> userOptional;

        try {
            userOptional = userDbDao.getSingleResultByUsername(username);
        }
        catch (NonUniqueResultException e) {
            e.printStackTrace();
            exceptionDao.save(new UsernameNotUniqueException());
            throw new UsernameNotFoundException("We have some problems. Sorry, try again later.");
        }

        return userOptional.orElseThrow(UserNotFoundException::new);
    }

    public List<User> getAllUsers() {
        return userDbDao.findAll();
    }

    public List<String> getAllUserLinks() {
        return getAllUsers()
                .stream()
                .map(User::getLink)
                .collect(Collectors.toList());
    }

    public User findUserById(UUID id) throws NonUniqueResultException, UserNotFoundException {
        Optional<User> userOptional = userDbDao.getSingleResultById(id);
        return userOptional.orElseThrow(UserNotFoundException::new);
    }

    public void signUp(UserSignUpDto userSignUpDto) throws IllegalArgumentException, DataIntegrityViolationException {
        userDbDao.save(User.valueOf(userSignUpDto));
    }
}
