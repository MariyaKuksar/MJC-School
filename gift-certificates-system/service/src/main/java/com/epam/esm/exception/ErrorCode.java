package com.epam.esm.exception;

public enum ErrorCode {
    GIFT_CERTIFICATE("01"),
    TAG("02");

    private final String errorCode;

    private ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
