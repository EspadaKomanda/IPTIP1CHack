package ru.espada.ep.iptip.exceptions.custom;

import lombok.Getter;

@Getter
public class SessionException extends RuntimeException {

    private String info;

    public SessionException(String message) {
        super(message);
        info = "";
    }

    public SessionException(String message, String info) {
        super(message);
        this.info = info;
    }
}
