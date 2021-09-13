package com.epam.esm.exception;

/**
 *  Class presents error details, such as message key for localization, incorrect parameter, error code.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
public class ErrorDetails {
    private String messageKey;
    private String incorrectParameter;
    private String ErrorCode;

    public ErrorDetails(String messageKey, String incorrectParameter, String errorCode) {
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
