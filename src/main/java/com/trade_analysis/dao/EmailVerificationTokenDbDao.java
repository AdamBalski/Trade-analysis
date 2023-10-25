package com.trade_analysis.dao;

import com.trade_analysis.model.EmailVerificationToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Qualifier("emailVerificationTokenDbDao")
@Transactional
public interface EmailVerificationTokenDbDao extends JpaRepository<EmailVerificationToken, UUID> {
    Optional<EmailVerificationToken> getSingleResultById(UUID id);

    @Modifying
    @Query(value =
            "DELETE " +
                    "FROM \"user\" u " +
                    "WHERE u.id IN (" +
                    "   SELECT e.user_id " +
                    "   FROM email_verification_token e " +
                    "   WHERE e.expiration_date < NOW()" +
                    ");" +

                    "-- It deletes tokens to, because there is ON DELETE CASCADE on relation",
            nativeQuery = true
    )
    int deleteOutdatedTokensWithRelatedUsers();
}
