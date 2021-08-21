package com.epam.esm.validator;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.MessageKey;
import org.apache.commons.lang3.StringUtils;

public final class TagValidator {

    private TagValidator() {
    }

    public static void validateId(long id) {
        if (id < ValidValue.MIN_ID) {
            throw new IncorrectParamValueException("invalid id = " + id, MessageKey.INCORRECT_ID, String.valueOf(id),
                    ErrorCode.TAG.getErrorCode());
        }
    }

    public static void validateName(String name) {
        if (StringUtils.isBlank(name) || name.length() > ValidValue.MAX_LENGTH_NAME) {
            throw new IncorrectParamValueException("invalid name = " + name, MessageKey.INCORRECT_NAME, name,
                    ErrorCode.TAG.getErrorCode());
        }
    }
}
