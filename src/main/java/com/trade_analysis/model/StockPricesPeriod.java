package com.trade_analysis.model;

public enum StockPricesPeriod {
    FIVE_MINUTES_PERIOD("FIVE_MINUTES_PERIOD", "5 min period"),
    HOUR_PERIOD("HOUR_PERIOD", "Hour period"),
    DAILY("DAILY", "Daily");

    String stringRepresentation;
    String prettified;

    StockPricesPeriod(String stringRepresentation, String prettified) {
        this.stringRepresentation = stringRepresentation;
        this.prettified = prettified;
    }

    public String getPrettified() {
        return this.prettified;
    }
}