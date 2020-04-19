package com.trade_analysis.exception;

import java.util.function.Supplier;

// It's thrown when app recognizes that usernames aren't unique
public class UsernameNotUniqueException extends Exception {
    public static final Supplier<UsernameNotUniqueException> USERNAME_NOT_UNIQUE_EXCEPTION_SUPPLIER = UsernameNotUniqueException::new;

    public UsernameNotUniqueException() {
        this("Username isn't unique.");
    }

    public UsernameNotUniqueException(String message) {
        super(message);
    }
}
