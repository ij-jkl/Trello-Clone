package org.trello.trelloclone.dtos;

public class InvalidEntityException extends RuntimeException {
    public InvalidEntityException(String message) {
        super(message);
    }
}