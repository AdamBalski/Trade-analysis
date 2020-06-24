package com.trade_analysis.dao;

import com.trade_analysis.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Qualifier("userDbDao")
@Transactional
public interface UserDbDao extends JpaRepository<User, UUID> {
    Optional<User> getSingleResultByUsername(String username);
    Optional<User> getSingleResultById(UUID id);
    Optional<User> getSingleResultByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
