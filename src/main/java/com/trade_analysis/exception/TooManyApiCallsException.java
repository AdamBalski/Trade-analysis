package com.trade_analysis.exception;

public class TooManyApiCallsException extends Exception {
    public TooManyApiCallsException() {
        this("They have been performed too many api calls for one minute.");
    }

    public TooManyApiCallsException(String message) {
        super(message);
    }
}
