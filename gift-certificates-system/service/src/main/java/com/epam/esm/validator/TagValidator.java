package com.epam.esm.validator;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ErrorDetails;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.MessageKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Class provides methods to validate fields of tag.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
@Component
public class TagValidator {

    /**
     * Validates tag id
     *
     * @param id the tag id
     */
    public void validateId(long id) {
        if (id < ValidValue.MIN_ID) {
            ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_ID, String.valueOf(id),
                    ErrorCode.TAG_INVALID_ID.getErrorCode());
            throw new IncorrectParamValueException("invalid id = " + id, Arrays.asList(errorDetails));
        }
    }

    /**
     * Validates tag name
     *
     * @param name the tag name
     */
    public void validateName(String name) {
        if (StringUtils.isBlank(name)) {
            ErrorDetails errorDetails = new ErrorDetails(MessageKey.EMPTY_TAG_NAME, name,
                    ErrorCode.TAG_INVALID_NAME.getErrorCode());
            throw new IncorrectParamValueException("invalid name = " + name, Arrays.asList(errorDetails));
        } else if (name.length() > ValidValue.MAX_LENGTH_NAME) {
            ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_TAG_NAME, name,
                    ErrorCode.TAG_INVALID_NAME.getErrorCode());
            throw new IncorrectParamValueException("invalid name = " + name, Arrays.asList(errorDetails));
        }
    }
}
