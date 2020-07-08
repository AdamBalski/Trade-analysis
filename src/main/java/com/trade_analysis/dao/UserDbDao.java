package com.trade_analysis.dao;

import com.trade_analysis.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Qualifier("userDbDao")
@Transactional
public interface UserDbDao extends JpaRepository<User, UUID> {
    Optional<User> getSingleResultByUsername(String username);
    Optional<User> getSingleResultById(UUID id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Modifying
    @Query(value =
            "UPDATE \"user\" " +
                    "SET api_key = :key " +
                    "WHERE username = :username ;",
            nativeQuery = true)
    void updateApiKey(@Param(value = "username") String username, @Param(value = "key") String abbpiKey);
}
