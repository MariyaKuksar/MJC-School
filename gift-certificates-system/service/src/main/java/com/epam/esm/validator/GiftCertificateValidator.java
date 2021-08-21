package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.MessageKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

public final class GiftCertificateValidator {

    private GiftCertificateValidator() {
    }

    public static void validateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        validateName(giftCertificateDto.getName());
        validateDescription(giftCertificateDto.getDescription());
        validatePrice(giftCertificateDto.getPrice());
        validateDuration(giftCertificateDto.getDuration());
        List<TagDto> tagDtoList = giftCertificateDto.getTags();
        if (tagDtoList != null && !tagDtoList.isEmpty()) {
            tagDtoList.forEach(tagDto -> TagValidator.validateName(tagDto.getName()));
        }
    }

    public static void validateId(long id) {
        if (id < ValidValue.MIN_ID) {
            throw new IncorrectParamValueException("invalid id = " + id, MessageKey.INCORRECT_ID, String.valueOf(id),
                    ErrorCode.GIFT_CERTIFICATE.getErrorCode());
        }
    }

    public static void validateName(String name) {
        if (StringUtils.isBlank(name) || name.length() > ValidValue.MAX_LENGTH_NAME) {
            throw new IncorrectParamValueException("invalid name = " + name, MessageKey.INCORRECT_NAME, name,
                    ErrorCode.GIFT_CERTIFICATE.getErrorCode());
        }
    }

    public static void validateDescription(String description) {
        if (StringUtils.isBlank(description) || description.length() > ValidValue.MAX_LENGTH_DESCRIPTION) {
            throw new IncorrectParamValueException("invalid description = " + description,
                    MessageKey.INCORRECT_DESCRIPTION, description, ErrorCode.GIFT_CERTIFICATE.getErrorCode());
        }
    }

    public static void validatePrice(BigDecimal price) {
        if (price == null || price.scale() > ValidValue.MAX_SCALE_PRICE || price.compareTo(ValidValue.MIN_PRICE) < 0 || price.compareTo(ValidValue.MAX_PRICE) > 0) {
            throw new IncorrectParamValueException("invalid price = " + price, MessageKey.INCORRECT_PRICE,
                    price.toString(), ErrorCode.GIFT_CERTIFICATE.getErrorCode());
        }
    }

    public static void validateDuration(int duration) {
        if (duration < ValidValue.MIN_DURATION || duration > ValidValue.MAX_DURATION) {
            throw new IncorrectParamValueException("invalid duration = " + duration, MessageKey.INCORRECT_DURATION,
                    String.valueOf(duration), ErrorCode.GIFT_CERTIFICATE.getErrorCode());
        }
    }
}