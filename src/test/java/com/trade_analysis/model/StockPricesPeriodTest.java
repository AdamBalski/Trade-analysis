package com.trade_analysis.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockPricesPeriodTest {
    @ParameterizedTest
    @CsvSource(value = {
            "FIVE_MINUTES_PERIOD, '5 min period'",
            "HOUR_PERIOD, 'Hour period'",
            "DAILY, 'Daily'"
    })
    void testGetPrettified(StockPricesPeriod stockPricesPeriod, String prettified) {
        assertEquals(stockPricesPeriod.getPrettified(), prettified);
    }
}
