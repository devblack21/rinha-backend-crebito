package com.devblack21.rinha.backend.crebito.controller.handler;

import br.com.fluentvalidator.exception.ValidationException;
import com.devblack21.rinha.backend.crebito.core.exception.NotFoundException;
import com.devblack21.rinha.backend.crebito.core.exception.NotLimitException;
import io.github.devblack21.logging.LogBit;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@ControllerAdvice
public class ExceptionControllerHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentExceptionHandler(final IllegalArgumentException ex, WebRequest request) {

        final Map<String, Object> errorObjectDTO = new HashMap<>();

        errorObjectDTO.put("message", ex.getMessage());
        errorObjectDTO.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(errorObjectDTO);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> inspectNotFoundException(final NotFoundException exception,
                                                      final WebRequest request) {

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NotLimitException.class)
    public ResponseEntity<?> inspectNotLimitException(final NotLimitException exception,
                                                      final WebRequest request) {

        LogBit.error("TRANSACTION", exception.getMessage());
        final Map<String, Object> errorObjectDTO = new HashMap<>();

        errorObjectDTO.put("message", exception.getMessage());
        errorObjectDTO.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());

        return ResponseEntity.unprocessableEntity().body(errorObjectDTO);
    }



    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> inspectValidationExceptionHandler(final ValidationException ex, WebRequest request) {

        final Map<String, Object> errorObjectDTO = new HashMap<>();

        errorObjectDTO.put("message", "Invalid values");
        errorObjectDTO.put("status", HttpStatus.BAD_REQUEST.value());

        final List<Map<String, Object>> errors = new ArrayList<>();

        ex.getValidationResult().getErrors().forEach(error -> {
            final Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("field", error.getField());
            errorMap.put("message", error.getMessage());
            Optional.ofNullable(error.getAttemptedValue())
                    .ifPresent(value -> errorMap.put("value", value));
            errors.add(errorMap);
        });

        if (!errors.isEmpty()) {
            errorObjectDTO.put("errors", errors);
        }

        return ResponseEntity.badRequest().body(errorObjectDTO);
    }


}
