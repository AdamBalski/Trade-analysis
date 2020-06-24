package com.trade_analysis.model;

import com.trade_analysis.TradeAnalysisApplication;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailVerificationTokenTest {
    @Test
    void testGetLink() {
        String expected = TradeAnalysisApplication.INTERNET_ADDRESS + "email-verification/ad123bfc-b6b4-4626-88bd-fd8a06d81c6c";

        UUID id = UUID.fromString("ad123bfc-b6b4-4626-88bd-fd8a06d81c6c");
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken(id, null);

        assertEquals(expected, emailVerificationToken.getLink());
    }
}