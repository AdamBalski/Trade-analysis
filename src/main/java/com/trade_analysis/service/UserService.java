package com.trade_analysis.service;

import com.trade_analysis.dao.ExceptionDao;
import com.trade_analysis.dao.UserDao;
import com.trade_analysis.exception.IdNotUniqueException;
import com.trade_analysis.exception.UserNotFoundException;
import com.trade_analysis.exception.UsernameNotUniqueException;
import com.trade_analysis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class UserService {
    @Autowired
    @Qualifier("userDbDao")
    UserDao userDao;

    public User getUserByUsername(String username) throws UserNotFoundException, UsernameNotUniqueException{
        return userDao.getUserByUsername(username);
    }

    public List<User> getAllUsers() {
        return userDao.findAllUsers();
    }

    public List<String> getAllUserLinks() {
        return getAllUsers()
                .stream()
                .map(User::getLink)
                .collect(Collectors.toList());
    }

    public User findUserById(UUID id) throws IdNotUniqueException, UserNotFoundException {
        return userDao.findUserById(id);
    }
}
