package com.trade_analysis.model;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public enum StockSymbol {
    MSFT("MSFT"),
    AMZN("AMZN"),
    IBM("IBM"),
    CSCO("CSCO"),
    AAPL("AAPL");

    String sys;

    StockSymbol(String sys) {
        this.sys = sys;
    }
}

