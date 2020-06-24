package com.trade_analysis.dao;

import com.trade_analysis.model.EmailVerificationToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Qualifier("emailVerificationTokenDbDao")
@Transactional
public interface EmailVerificationTokenDbDao extends JpaRepository<EmailVerificationToken, UUID> {
    Optional<EmailVerificationToken> getSingleResultById(UUID id);
}
