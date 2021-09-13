package com.epam.esm.exception;

/**
 * Thrown to indicate that unique param already exists.
 *
 * @author Maryia Kuksar
 * @version 1.0
 * @see RuntimeException
 */
public class ResourceAlreadyExistsException extends RuntimeException{
    private ErrorDetails errorDetails;

    /**
     * Constructor a ResourceAlreadyExistsException with the specified detail message and error details
     *
     * @param message the detail message
     * @param errorDetails the error details
     */
    public ResourceAlreadyExistsException(String message, ErrorDetails errorDetails) {
        super(message);
        this.errorDetails = errorDetails;
    }

    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }
}
