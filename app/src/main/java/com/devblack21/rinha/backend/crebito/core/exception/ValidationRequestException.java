package com.devblack21.rinha.backend.crebito.core.exception;

import br.com.fluentvalidator.context.ValidationResult;
import br.com.fluentvalidator.exception.ValidationException;

public class ValidationRequestException extends ValidationException {
    public ValidationRequestException(final ValidationResult validationResult) {
        super(validationResult);
    }
}
