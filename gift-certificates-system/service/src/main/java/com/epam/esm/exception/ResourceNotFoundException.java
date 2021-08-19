package com.epam.esm.exception;

public class ResourceNotFoundException extends RuntimeException {
    private String messageKey;
    private String incorrectParameter;
    private String ErrorCode;

    public ResourceNotFoundException(String message, String messageKey, String incorrectParameter, String errorCode) {
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
