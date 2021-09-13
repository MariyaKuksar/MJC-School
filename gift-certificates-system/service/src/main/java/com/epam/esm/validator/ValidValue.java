package com.epam.esm.validator;

import java.math.BigDecimal;

/**
 * Describes all valid value for validation.
 *
 * @author Mariya Kuksar
 * @version 1.0
 */
public final class ValidValue {
    public static final int MIN_ID = 1;
    public static final int MAX_LENGTH_NAME = 45;
    public static final int MAX_LENGTH_DESCRIPTION = 100;
    public static final int MAX_SCALE_PRICE = 2;
    public static final BigDecimal MIN_PRICE = new BigDecimal("0.01");
    public static final BigDecimal MAX_PRICE = new BigDecimal("999999.99");
    public static final int MIN_DURATION = 1;
    public static final int MAX_DURATION = 1000;

    private ValidValue(){

    }
}
