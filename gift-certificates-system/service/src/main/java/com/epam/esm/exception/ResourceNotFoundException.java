package com.epam.esm.exception;

/**
 * Thrown to indicate that resource is not found.
 *
 * @author Maryia Kuksar
 * @version 1.0
 * @see RuntimeException
 */
public class ResourceNotFoundException extends RuntimeException {
    private ErrorDetails errorDetails;

    /**
     * Constructor a ResourceNotFoundException with the specified detail message and error details
     *
     * @param message the detail message
     * @param errorDetails the error details
     */
    public ResourceNotFoundException(String message, ErrorDetails errorDetails) {
        super(message);
        this.errorDetails = errorDetails;
    }

    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }
}
