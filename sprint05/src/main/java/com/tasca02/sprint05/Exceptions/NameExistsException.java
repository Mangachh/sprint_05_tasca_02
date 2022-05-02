package com.tasca02.sprint05.Exceptions;

public class NameExistsException extends RuntimeException {
    private final String MESSAGE = "The name already exists";

    @Override
    public String getMessage() {
        return this.MESSAGE;
    }
    
}
