package com.epam.esm.exception;

import java.util.Collections;
import java.util.List;

/**
 * Thrown to indicate that params is incorrect.
 *
 * @author Maryia Kuksar
 * @version 1.0
 * @see RuntimeException
 */
public class IncorrectParamValueException extends RuntimeException {
    private List<ErrorDetails> errors;

    /**
     * Constructor a IncorrectParamValueException with the specified detail message and list of error details
     *
     * @param message the detail message
     * @param errors the list of error details
     */
        public IncorrectParamValueException(String message, List<ErrorDetails> errors) {
        super(message);
        this.errors = errors;
    }

    public List<ErrorDetails> getErrors() {
        return errors == null ? Collections.emptyList() : Collections.unmodifiableList(errors);
    }
}
