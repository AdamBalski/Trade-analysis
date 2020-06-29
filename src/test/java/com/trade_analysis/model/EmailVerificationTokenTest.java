package com.trade_analysis.model;

import com.trade_analysis.TradeAnalysisApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static com.trade_analysis.model.UserRole.USUAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EmailVerificationTokenTest {
    private EmailVerificationToken simple1;
    private EmailVerificationToken simple2;
    private EmailVerificationToken simple3;

    @BeforeEach
    void init() {
        User user1 = new User(UUID.fromString("fd91c269-ab5c-4f8a-907a-e7f044239781"), "username3", "username3@email.com",
                "$2y$10$CS.lGeJ7JyUQdkUl06Gt4uGb1jahebbYvc5EFYDT0BtZ.0uCbtGoy", USUAL, "RCAK7TCIWA32NQTY");

        UUID id1 = UUID.fromString("ad123bfc-b6b4-4626-88bd-fd8a06d81c6c");
        simple1 = new EmailVerificationToken(id1, user1);

        User user2 = new User(UUID.fromString("fd91c269-ab5c-4f8a-907a-e7f044239781"), "username3", "username3@email.com",
                "$2y$10$CS.lGeJ7JyUQdkUl06Gt4uGb1jahebbYvc5EFYDT0BtZ.0uCbtGoy", USUAL, "RCAK7TCIWA32NQTY");

        UUID id2 = UUID.fromString("ad123bfc-b6b4-4626-88bd-fd8a06d81c6c");
        simple2 = new EmailVerificationToken(id2, user2);

        User user3 = new User(UUID.fromString("48ee79d6-3350-4ab4-a33f-f176051741e4"), "username4", "username4@email.com",
                "$2y$10$f0D5rrsIjmCfWLLcfi7XP.LERwZyjSfircn9tAj0NWestb.qR6FKS", USUAL, "P11ZRHMSXHJ442NS");

        UUID id3 = UUID.fromString("0e4d3610-4d4d-42b5-b92f-85849d404c4b");
        simple3 = new EmailVerificationToken(id3, user3);
    }

    @Test
    void testGetUserId() {
        UUID expected = UUID.fromString("fd91c269-ab5c-4f8a-907a-e7f044239781");
        assertEquals(expected, simple1.getUserId());
    }

    @Test
    void testGetLink() {
        String expected = TradeAnalysisApplication.INTERNET_ADDRESS + "email-verification/ad123bfc-b6b4-4626-88bd-fd8a06d81c6c";

        assertEquals(expected, simple1.getLink());
    }

    @Test
    void testEqualsWithNull() {
        assertNotEquals(simple1, null);
    }

    @Test
    void testEqualsWithNotEmailVerificationToken() {
        assertNotEquals(simple1, new ArrayList<>());
    }

    @Test
    void testEqualsWithItself() {
        assertEquals(simple1, simple1);
    }

    @Test
    void testEqualsWhenItShouldReturnTrue() {
        assertEquals(simple1, simple2);
        assertEquals(simple2, simple1);
    }

    @Test
    void testEqualsWhenItShouldReturnFalse() {
        assertNotEquals(simple1, simple3);
        assertNotEquals(simple3, simple2);
    }

    @Test
    void testHashcodeOnEqualTokensIsEqual() {
        assertEquals(simple1.hashCode(), simple2.hashCode());
    }
}