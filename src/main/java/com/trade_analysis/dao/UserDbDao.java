package com.trade_analysis.dao;

import com.trade_analysis.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NonUniqueResultException;
import java.util.Optional;
import java.util.UUID;

@Qualifier("userDbDao")
@Transactional
public interface UserDbDao extends JpaRepository<User, UUID> {
    Optional<User> getSingleResultByUsername(String username) throws NonUniqueResultException;
    Optional<User> getSingleResultById(UUID id) throws NonUniqueResultException;
}
