package com.trade_analysis.model;

import com.trade_analysis.TradeAnalysisApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.Period;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "email_verification_token", schema = "public")
public class EmailVerificationToken {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "uuid")
    private UUID id = UUID.randomUUID();

    @OneToOne(targetEntity = User.class)
    @JoinColumn(nullable = false, name = "user_id", updatable = false, unique = true)
    private User user;

    @Column(nullable = false, name = "expiration_date")
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    public EmailVerificationToken(User user) {
        this(UUID.randomUUID(), user);
    }

    public EmailVerificationToken(UUID uuid, User user) {
        this(
                uuid,
                user,
                Date.from(Instant.now().plus(Period.ofDays(2)))
        );
    }

    public UUID getUserId() {
        return user.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailVerificationToken)) return false;

        EmailVerificationToken that = (EmailVerificationToken) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(user, that.user);
    }

    public String getLink() {
        return TradeAnalysisApplication.INTERNET_ADDRESS + "email-verification/" + id.toString();
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}

