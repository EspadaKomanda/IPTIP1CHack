package ru.espada.ep.iptip.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.espada.ep.iptip.exceptions.custom.LocalizationException;
import ru.espada.ep.iptip.exceptions.response.OperationStatus;

@ControllerAdvice
public class LocalizationExceptionAdvice {

    @ExceptionHandler(LocalizationException.class)
    public ResponseEntity<?> handleLocalizationException(LocalizationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                OperationStatus.builder()
                        .status(false)
                        .message(e.getMessage())
                        .build()
        );
    }

}
