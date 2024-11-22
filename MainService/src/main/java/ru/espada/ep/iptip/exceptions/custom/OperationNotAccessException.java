package ru.espada.ep.iptip.exceptions.custom;

public class OperationNotAccessException extends RuntimeException {

    public OperationNotAccessException(String message) {
        super(message);
    }
}
