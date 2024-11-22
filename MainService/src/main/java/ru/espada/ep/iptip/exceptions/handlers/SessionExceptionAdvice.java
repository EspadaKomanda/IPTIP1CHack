package ru.espada.ep.iptip.exceptions.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.espada.ep.iptip.exceptions.custom.SessionException;
import ru.espada.ep.iptip.exceptions.response.OperationStatus;
import ru.espada.ep.iptip.localization.LocalizationService;

@ControllerAdvice
public class SessionExceptionAdvice {

    @Autowired
    private LocalizationService localizationService;

    @ExceptionHandler(SessionException.class)
    public ResponseEntity<?> handleRuntimeException(SessionException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                OperationStatus.builder()
                        .status(false)
                        .message(String.format(localizationService.getLocalizedMessage(e.getMessage()), e.getInfo()))
                        .build()
        );
    }

}
