package com.devblack21.rinha.backend.crebito.controller.handler;

import br.com.fluentvalidator.exception.ValidationException;
import com.devblack21.rinha.backend.crebito.core.exception.NotFoundException;
import com.devblack21.rinha.backend.crebito.core.exception.NotLimitException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionControllerHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentExceptionHandler(final IllegalArgumentException ex, WebRequest request) {

        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> inspectNotFoundException(final NotFoundException exception,
                                                      final WebRequest request) {

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NotLimitException.class)
    public ResponseEntity<?> inspectNotLimitException(final NotLimitException exception,
                                                      final WebRequest request) {

        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> inspectValidationExceptionHandler(final ValidationException ex, WebRequest request) {

        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> inspectHttpMessageNotReadableException(final HttpMessageNotReadableException exception,
                                                                    final WebRequest request) {
        return ResponseEntity.unprocessableEntity().build();
    }

}
