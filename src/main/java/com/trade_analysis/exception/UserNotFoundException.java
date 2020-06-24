package com.trade_analysis.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        this("User could not be found.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
