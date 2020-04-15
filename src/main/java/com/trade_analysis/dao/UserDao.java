package com.trade_analysis.dao;

import com.trade_analysis.exception.IdNotUniqueException;
import com.trade_analysis.exception.UserNotFoundException;
import com.trade_analysis.exception.UsernameNotUniqueException;
import com.trade_analysis.model.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    User getUserByUsername(String username) throws UserNotFoundException, UsernameNotUniqueException;
    List<User> findAllUsers();
    User findUserById(UUID uuid) throws UserNotFoundException, IdNotUniqueException;
}
