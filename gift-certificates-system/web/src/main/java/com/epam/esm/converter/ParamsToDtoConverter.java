package com.epam.esm.converter;

import com.epam.esm.dto.PaginationDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ErrorDetails;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.MessageKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ParamsToDtoConverter {
    private static final String NUMBER = "number";
    private static final String DEFAULT_NUMBER = "1";
    private static final String SIZE = "size";
    private static final String DEFAULT_SIZE = "5";
    private static final String NUMBER_AND_SIZE_PATTERN = "^[1-9][0-9]{0,8}$";

    public PaginationDto getPaginationDto(Map<String, String> pageParams) {
        String number = StringUtils.isBlank(pageParams.get(NUMBER)) ? DEFAULT_NUMBER : pageParams.get(NUMBER);
        String size = StringUtils.isBlank(pageParams.get(SIZE)) ? DEFAULT_SIZE : pageParams.get(SIZE);
        checkParamsValue(number, size);
        int offset = ((Integer.valueOf(number) - 1)) * Integer.valueOf(size);
        int limit = Integer.valueOf(size);
        return new PaginationDto(offset, limit);
    }

    private void checkParamsValue(String number, String size) {
        List<ErrorDetails> errors = new ArrayList<>();
        if (!number.matches(NUMBER_AND_SIZE_PATTERN)) {
            ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_PAGE_NUMBER, String.valueOf(number),
                    ErrorCode.PAGE_INVALID_NUMBER.getErrorCode());
            errors.add(errorDetails);
        }
        if (!size.matches(NUMBER_AND_SIZE_PATTERN)) {
            ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_PAGE_SIZE, String.valueOf(size),
                    ErrorCode.PAGE_INVALID_SIZE.getErrorCode());
            errors.add(errorDetails);
        }
        if (!errors.isEmpty()) {
            throw new IncorrectParamValueException("invalid page params", errors);
        }
    }
}
