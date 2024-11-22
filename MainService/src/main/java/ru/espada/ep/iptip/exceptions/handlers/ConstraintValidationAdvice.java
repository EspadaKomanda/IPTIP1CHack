package ru.espada.ep.iptip.exceptions.handlers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.espada.ep.iptip.exceptions.custom.LocalizationException;
import ru.espada.ep.iptip.exceptions.response.OperationStatus;
import ru.espada.ep.iptip.localization.LocalizationService;

@ControllerAdvice
public class ConstraintValidationAdvice {

    private LocalizationService localizationService;

    @Autowired
    public void setLocalizationService(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @ExceptionHandler
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().iterator().next().getMessage();
        try {
            message = localizationService.getLocalizedMessage(message);
        } catch (LocalizationException ignored) {
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                OperationStatus.builder()
                        .status(false)
                        .message(message)
                        .build()
        );
    }

}
