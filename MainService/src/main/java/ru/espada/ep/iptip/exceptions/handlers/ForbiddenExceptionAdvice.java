package ru.espada.ep.iptip.exceptions.handlers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.espada.ep.iptip.exceptions.custom.ForbiddenException;
import ru.espada.ep.iptip.exceptions.custom.LocalizationException;
import ru.espada.ep.iptip.exceptions.response.OperationStatus;
import ru.espada.ep.iptip.localization.LocalizationService;

@ControllerAdvice
public class ForbiddenExceptionAdvice {

    private LocalizationService localizationService;

    @Autowired
    public void setLocalizationService(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @ExceptionHandler
    public ResponseEntity<?> handleForbiddenException(ForbiddenException e) {
        String message = e.getMessage();
        try {
            message = localizationService.getLocalizedMessage(message);
        } catch (LocalizationException ignored) {
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                OperationStatus.builder()
                        .status(false)
                        .message(message)
                        .build()
        );
    }

}
