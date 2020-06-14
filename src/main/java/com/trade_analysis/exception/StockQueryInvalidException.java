package com.trade_analysis.exception;

public class StockQueryInvalidException extends Exception {
    public StockQueryInvalidException() {
        this("Stock Query is invalid.");
    }

    public StockQueryInvalidException(String message) {
        super(message);
    }
}
