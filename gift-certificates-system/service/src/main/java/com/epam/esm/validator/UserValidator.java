package com.epam.esm.validator;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ErrorDetails;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.MessageKey;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserValidator {

    public void validateId(long id) {
        if (id < ValidValue.MIN_ID) {
            ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_ID, String.valueOf(id),
                    ErrorCode.USER_INVALID_ID.getErrorCode());
            throw new IncorrectParamValueException("invalid id = " + id, Arrays.asList(errorDetails));
        }
    }
}
