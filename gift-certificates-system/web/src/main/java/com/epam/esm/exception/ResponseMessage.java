package com.epam.esm.exception;

public final class ResponseMessage {
    private final String errorMessage;
    private final String errorCode;

    public ResponseMessage(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
