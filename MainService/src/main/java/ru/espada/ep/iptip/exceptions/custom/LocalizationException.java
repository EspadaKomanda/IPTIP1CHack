package ru.espada.ep.iptip.exceptions.custom;

import lombok.Getter;

@Getter
public class LocalizationException extends RuntimeException {

    private final String language;

    public LocalizationException(String message, String language) {
        super(message);
        this.language = language;
    }
}
