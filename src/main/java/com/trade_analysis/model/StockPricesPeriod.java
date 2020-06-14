package com.trade_analysis.model;

import java.time.Duration;

import static java.time.Duration.*;

public enum StockPricesPeriod {
    FIVE_MINUTES_PERIOD("FIVE_MINUTES_PERIOD", "5 min period", ofMinutes(5)),
    HOUR_PERIOD("HOUR_PERIOD", "Hour period", ofHours(1)),
    DAILY("DAILY", "Daily", ofDays(1));

    String stringRepresentation;
    String prettified;
    Duration duration;

    StockPricesPeriod(String stringRepresentation, String prettified, Duration duration) {
        this.stringRepresentation = stringRepresentation;
        this.prettified = prettified;
        this.duration = duration;
    }

    public String getPrettified() {
        return this.prettified;
    }
}