package com.trade_analysis.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        this("User wasn't found.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
