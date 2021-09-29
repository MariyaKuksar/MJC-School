package com.epam.esm.exception;

/**
 * Class presents custom error code which is based on gift certificate or tag field, which is incorrect.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
public enum ErrorCode {
    GIFT_CERTIFICATE_INVALID_ID ("01"),
    GIFT_CERTIFICATE_INVALID_NAME ("02"),
    GIFT_CERTIFICATE_INVALID_DESCRIPTION ("03"),
    GIFT_CERTIFICATE_INVALID_PRICE("04"),
    GIFT_CERTIFICATE_INVALID_DURATION ("05"),
    TAG_INVALID_ID ("06"),
    TAG_INVALID_NAME ("07"),
    USER_INVALID_ID ("08"),
    PAGE_INVALID_NUMBER ("09"),
    PAGE_INVALID_SIZE ("10"),
    INVALID_SORTING_ORDER_BY_DATE ("11"),
    INVALID_SORTING_ORDER_BY_NAME ("12"),
    NO_FIELDS_TO_UPDATE ("13");

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
