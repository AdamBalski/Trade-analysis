package com.trade_analysis.dao;

import com.trade_analysis.model.User;

import javax.persistence.NonUniqueResultException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    Optional<User> getSingleResultByUsername(String username) throws NonUniqueResultException;
    List<User> findAll();
    Optional<User> getSingleResultById(UUID uuid) throws NonUniqueResultException;
    boolean existsById(UUID id);
}
