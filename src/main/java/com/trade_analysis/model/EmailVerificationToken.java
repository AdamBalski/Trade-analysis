package com.trade_analysis.model;

import com.trade_analysis.TradeAnalysisApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public EmailVerificationToken(User user) {
        this(UUID.randomUUID(), user);
    }

    public String getLink() {
        return TradeAnalysisApplication.INTERNET_ADDRESS + "email-verification/" + id.toString();
    }
}

