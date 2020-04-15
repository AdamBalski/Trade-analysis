package com.trade_analysis.exception;

// It's thrown when app recognizes that emails aren't unique
public class EmailNotUniqueException extends Exception {
    public EmailNotUniqueException() {
        this("Email isn't unique.");
    }

    public EmailNotUniqueException(String message) {
        super(message);
    }
}
