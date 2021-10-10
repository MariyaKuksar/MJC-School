package com.epam.esm.validator;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ErrorDetails;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.MessageKey;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class provides methods to validate fields of {@link com.epam.esm.dto.UserDto}
 *
 * @author Maryia_Kuksar
 * @version 1.0
 */
@Component
public class UserValidator {
    private static final Logger logger = LogManager.getLogger();
    private static final String EMAIL_PATTERN = "^([.[^@\\sА-Яа-я]]+)@([.[^@\\sА-Яа-я]]+)\\.([a-z]+)$";
    private static final String PASSWORD_PATTERN = "^[a-zA-Z\\d]{4,15}$";

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

    public void validateUser(UserDto userDto) {
        List<ErrorDetails> errors = new ArrayList<>();
        validateName(userDto.getName(), errors);
        validateEmail(userDto.getEmail(), errors);
        validatePassword(userDto.getPassword(), errors);
        if (!errors.isEmpty()) {
            throw new IncorrectParamValueException("invalid user params", errors);
        }
    }

    private void validatePassword(String password, List<ErrorDetails> errors) {
        if (StringUtils.isBlank(password)) {
            errors.add(new ErrorDetails(MessageKey.EMPTY_PASSWORD, password,
                    ErrorCode.USER_INVALID_PASSWORD.getErrorCode()));
            logger.error("user password error, invalid password = " + password);
        } else if (!password.matches(PASSWORD_PATTERN)) {
            errors.add(new ErrorDetails(MessageKey.INCORRECT_PASSWORD, password,
                    ErrorCode.USER_INVALID_PASSWORD.getErrorCode()));
            logger.error("user password error, invalid password = " + password);
        }
    }

    private void validateEmail(String email, List<ErrorDetails> errors) {
        if (StringUtils.isBlank(email)) {
            errors.add(new ErrorDetails(MessageKey.EMPTY_EMAIL, email,
                    ErrorCode.USER_INVALID_EMAIL.getErrorCode()));
            logger.error("user email error, invalid email = " + email);
        } else if (email.length() > ValidValue.MAX_LENGTH_NAME || !email.matches(EMAIL_PATTERN)) {
            errors.add(new ErrorDetails(MessageKey.INCORRECT_EMAIL, email,
                    ErrorCode.USER_INVALID_EMAIL.getErrorCode()));
            logger.error("user email error, invalid email = " + email);
        }
    }

    private void validateName(String name, List<ErrorDetails> errors) {
        if (StringUtils.isBlank(name)) {
            errors.add(new ErrorDetails(MessageKey.EMPTY_NAME, name,
                    ErrorCode.USER_INVALID_NAME.getErrorCode()));
            logger.error("user name error, invalid name = " + name);
        } else if (name.length() > ValidValue.MAX_LENGTH_NAME) {
            errors.add(new ErrorDetails(MessageKey.INCORRECT_NAME, name,
                    ErrorCode.USER_INVALID_NAME.getErrorCode()));
            logger.error("user name error, invalid name = " + name);
        }
    }
}
