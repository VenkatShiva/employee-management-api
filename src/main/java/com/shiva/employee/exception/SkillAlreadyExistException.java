package com.shiva.employee.exception;

public class SkillAlreadyExistException extends RuntimeException {
    public SkillAlreadyExistException(String message) {
        super(message);
    }
}
