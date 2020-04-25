package com.trade_analysis.exception;

// It's thrown when app recognizes that usernames aren't unique
public class UsernameNotUniqueException extends Exception {
    public UsernameNotUniqueException() {
        this("Username isn't unique.");
    }

    public UsernameNotUniqueException(String message) {
        super(message);
    }
}
