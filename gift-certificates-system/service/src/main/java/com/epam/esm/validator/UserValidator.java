package com.epam.esm.validator;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ErrorDetails;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.MessageKey;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Class provides methods to validate fields of {@link com.epam.esm.dto.UserDto}
 *
 * @author Maryia_Kuksar
 * @version 1.0
 */
@Component
public class UserValidator {

    /**
     * Validates user id
     *
     * @param id the user id
     */
    public void validateId(long id) {
        if (id < ValidValue.MIN_ID) {
            ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_ID, String.valueOf(id),
                    ErrorCode.USER_INVALID_ID.getErrorCode());
            throw new IncorrectParamValueException("invalid id = " + id, Arrays.asList(errorDetails));
        }
    }
}
