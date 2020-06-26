package com.trade_analysis.exception;

public class InvalidApiCallException extends Exception {
    public InvalidApiCallException() {
        this("There was performed an invalid api call.");
    }

    public InvalidApiCallException(String message) {
        super(message);
    }
}
