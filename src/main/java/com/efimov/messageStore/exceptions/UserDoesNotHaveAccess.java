package com.efimov.messageStore.exceptions;

public class UserDoesNotHaveAccess extends RuntimeException {
    public UserDoesNotHaveAccess(String message) {
        super(message);
    }
}
