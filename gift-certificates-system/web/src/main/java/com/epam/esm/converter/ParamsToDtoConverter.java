package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.entity.SortingOrder;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ErrorDetails;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.MessageKey;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ParamsToDtoConverter {
    private static final String NUMBER = "number";
    private static final String DEFAULT_NUMBER = "1";
    private static final String SIZE = "size";
    private static final String DEFAULT_SIZE = "5";
    private static final String NUMBER_AND_SIZE_PATTERN = "^[1-9][0-9]{0,8}$";
    private static final String TAG_NAMES = "tagNames";
    private static final String SEPARATOR_TAGS = ",";
    private static final String PART_NAME_OR_DESCRIPTION = "partNameOrDescription";
    private static final String SORTING_ORDER_BY_DATE = "sortingOrderByDate";
    private static final String SORTING_ORDER_BY_NAME = "sortingOrderByName";

    public PaginationDto getPaginationDto(Map<String, String> pageParams) {
        String number = StringUtils.isBlank(pageParams.get(NUMBER)) ? DEFAULT_NUMBER : pageParams.get(NUMBER);
        String size = StringUtils.isBlank(pageParams.get(SIZE)) ? DEFAULT_SIZE : pageParams.get(SIZE);
        checkPageParamsValue(number, size);
        int offset = ((Integer.parseInt(number) - 1)) * Integer.parseInt(size);
        int limit = Integer.parseInt(size);
        return new PaginationDto(offset, limit);
    }

    public GiftCertificateSearchParamsDto getGiftCertificatesSearchParamsDto(Map<String, String> params) {
        GiftCertificateSearchParamsDto searchParamsDto = new GiftCertificateSearchParamsDto();
        String tagNames = params.get(TAG_NAMES);
        if (StringUtils.isNotBlank(tagNames)) {
            List<String> tagNamesList = Arrays.asList(tagNames.split(SEPARATOR_TAGS));
            searchParamsDto.setTagNames(tagNamesList.stream()
                    .map(String::strip)
                    .collect(Collectors.toList()));
        }
        searchParamsDto.setPartNameOrDescription(params.get(PART_NAME_OR_DESCRIPTION));
        setSortingOrderParamsValue(params, searchParamsDto);
        return searchParamsDto;
    }

    private void setSortingOrderParamsValue(Map<String, String> params, GiftCertificateSearchParamsDto searchParamsDto) {
        String sortingOrderByDate = params.get(SORTING_ORDER_BY_DATE);
        String sortingOrderByName = params.get(SORTING_ORDER_BY_NAME);
        List<ErrorDetails> errors = new ArrayList<>();
        if (StringUtils.isNotBlank(sortingOrderByDate)) {
            if (isValidSortingOrder(sortingOrderByDate)) {
                searchParamsDto.setSortingOrderByDate(SortingOrder.valueOf(sortingOrderByDate.toUpperCase()));
            } else {
                ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_SORTING_ORDER_BY_DATE, sortingOrderByDate,
                        ErrorCode.INVALID_SORTING_ORDER_BY_DATE.getErrorCode());
                errors.add(errorDetails);
            }
        }
        if (StringUtils.isNotBlank(sortingOrderByName)) {
            if (isValidSortingOrder(sortingOrderByName)) {
                searchParamsDto.setSortingOrderByName(SortingOrder.valueOf(sortingOrderByName.toUpperCase()));
            } else {
                ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_SORTING_ORDER_BY_NAME, sortingOrderByName,
                        ErrorCode.INVALID_SORTING_ORDER_BY_NAME.getErrorCode());
                errors.add(errorDetails);
            }
        }
        if (!errors.isEmpty()) {
            throw new IncorrectParamValueException("invalid sorting order params", errors);
        }
    }

    private void checkPageParamsValue(String number, String size) {
        List<ErrorDetails> errors = new ArrayList<>();
        if (!number.matches(NUMBER_AND_SIZE_PATTERN)) {
            ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_PAGE_NUMBER, number,
                    ErrorCode.PAGE_INVALID_NUMBER.getErrorCode());
            errors.add(errorDetails);
        }
        if (!size.matches(NUMBER_AND_SIZE_PATTERN)) {
            ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_PAGE_SIZE, size,
                    ErrorCode.PAGE_INVALID_SIZE.getErrorCode());
            errors.add(errorDetails);
        }
        if (!errors.isEmpty()) {
            throw new IncorrectParamValueException("invalid page params", errors);
        }
    }

    private boolean isValidSortingOrder(String sortingOrderParamValue) {
        return StringUtils.isNotBlank(sortingOrderParamValue) && EnumUtils.isValidEnum(SortingOrder.class, sortingOrderParamValue.toUpperCase());
    }
}
