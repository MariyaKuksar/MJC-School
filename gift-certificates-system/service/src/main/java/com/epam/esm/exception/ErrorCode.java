package com.epam.esm.exception;

/**
 * Class presents custom error code which is based on entity field, which is incorrect.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
public enum ErrorCode {
    DEFAULT("00"),
    GIFT_CERTIFICATE_INVALID_ID("01"),
    GIFT_CERTIFICATE_INVALID_NAME("02"),
    GIFT_CERTIFICATE_INVALID_DESCRIPTION("03"),
    GIFT_CERTIFICATE_INVALID_PRICE("04"),
    GIFT_CERTIFICATE_INVALID_DURATION("05"),
    GIFT_CERTIFICATE_INVALID_QUANTITY("06"),
    TAG_INVALID_ID("07"),
    TAG_INVALID_NAME("08"),
    USER_INVALID_ID("09"),
    PAGE_INVALID_NUMBER("10"),
    PAGE_INVALID_SIZE("11"),
    INVALID_SORTING_ORDER_BY_DATE("12"),
    INVALID_SORTING_ORDER_BY_NAME("13"),
    NO_FIELDS_TO_UPDATE("14"),
    ORDER_INVALID_ID("15"),
    BAD_CREDENTIALS("16");

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
