package com.trade_analysis.exception;

// It's thrown when app recognizes that IDs aren't unique
public class IdNotUniqueException extends Exception {
    public IdNotUniqueException() {
        this("Id isn't unique.");
    }

    public IdNotUniqueException(String message) {
        super(message);
    }
}
