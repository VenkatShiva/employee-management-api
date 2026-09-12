package com.shiva.employee.exception;

public class ProjectAlreadyExistException extends RuntimeException {
    public ProjectAlreadyExistException(String message) {
        super(message);
    }
}
