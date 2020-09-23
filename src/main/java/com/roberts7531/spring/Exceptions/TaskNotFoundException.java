package com.roberts7531.spring.Exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id){
        super("Could not find Task "+ id);
    }
}
