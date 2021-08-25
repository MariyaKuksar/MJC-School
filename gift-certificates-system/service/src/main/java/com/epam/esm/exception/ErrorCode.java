package com.epam.esm.exception;

/**
 * Class presents custom error code which is based on requested resource.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
public enum ErrorCode {
    GIFT_CERTIFICATE("01"),
    TAG("02");

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
