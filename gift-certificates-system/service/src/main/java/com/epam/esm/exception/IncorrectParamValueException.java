package com.epam.esm.exception;

/**
 * Throwns to indicate that params is incorrect.
 *
 * @author Maryia Kuksar
 * @version 1.0
 * @see RuntimeException
 */
public class IncorrectParamValueException extends RuntimeException {
    private String messageKey;
    private String incorrectParameter;
    private String ErrorCode;

    /**
     * Constructor a IncorrectParamValueException with the specified detail message,
     * message key for localization, incorrect parameter, error code
     *
     * @param message the detail message
     * @param messageKey the message key for localization
     * @param incorrectParameter the incorrect parameter
     * @param errorCode the error code
     */
    public IncorrectParamValueException(String message, String messageKey, String incorrectParameter,
                                        String errorCode) {
        super(message);
        this.messageKey = messageKey;
        this.incorrectParameter = incorrectParameter;
        ErrorCode = errorCode;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getIncorrectParameter() {
        return incorrectParameter;
    }

    public String getErrorCode() {
        return ErrorCode;
    }
}
