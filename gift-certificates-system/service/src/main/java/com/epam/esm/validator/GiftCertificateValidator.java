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

/**
 * Class provides methods to validate fields of {@link GiftCertificateDto}.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
@Component
public class GiftCertificateValidator {

    /**
     * Validates all fields of gift certificates
     *
     * @param giftCertificateDto data for validating
     */
    public void validateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        validateName(giftCertificateDto.getName());
        validateDescription(giftCertificateDto.getDescription());
        validatePrice(giftCertificateDto.getPrice());
        validateDuration(giftCertificateDto.getDuration());
        List<TagDto> tagDtoList = giftCertificateDto.getTags();
        if (tagDtoList != null && !tagDtoList.isEmpty()) {
            tagDtoList.forEach(tagDto -> validateTagName(tagDto.getName()));
        }
    }

    /**
     * Validates gift certificate id
     *
     * @param id the gift certificate id
     */
    public void validateId(long id) {
        if (id < ValidValue.MIN_ID) {
            throw new IncorrectParamValueException("invalid id = " + id,
                    MessageKey.INCORRECT_ID, String.valueOf(id), ErrorCode.GIFT_CERTIFICATE.getErrorCode());
        }
    }

    /**
     * Validates gift certificate name
     *
     * @param name the gift certificate name
     */
    public void validateName(String name) {
        if (StringUtils.isBlank(name) || name.length() > ValidValue.MAX_LENGTH_NAME) {
            throw new IncorrectParamValueException("invalid name = " + name,
                    MessageKey.INCORRECT_NAME, name, ErrorCode.GIFT_CERTIFICATE.getErrorCode());
        }
    }

    /**
     * Validates gift certificate description
     *
     * @param description the gift certificate description
     */
    public void validateDescription(String description) {
        if (StringUtils.isBlank(description) || description.length() > ValidValue.MAX_LENGTH_DESCRIPTION) {
            throw new IncorrectParamValueException("invalid description = " + description,
                    MessageKey.INCORRECT_DESCRIPTION, description, ErrorCode.GIFT_CERTIFICATE.getErrorCode());
        }
    }

    /**
     * Validates gift certificate price
     *
     * @param price the gift certificate price
     */
    public void validatePrice(BigDecimal price) {
        if (price == null || price.scale() > ValidValue.MAX_SCALE_PRICE || price.compareTo(ValidValue.MIN_PRICE) < 0 || price.compareTo(ValidValue.MAX_PRICE) > 0) {
            throw new IncorrectParamValueException("invalid price = " + price,
                    MessageKey.INCORRECT_PRICE, String.valueOf(price), ErrorCode.GIFT_CERTIFICATE.getErrorCode());
        }
    }

    /**
     * Validates gift certificate duration
     *
     * @param duration the gift certificate duration
     */
    public void validateDuration(int duration) {
        if (duration < ValidValue.MIN_DURATION || duration > ValidValue.MAX_DURATION) {
            throw new IncorrectParamValueException("invalid duration = " + duration,
                    MessageKey.INCORRECT_DURATION, String.valueOf(duration), ErrorCode.GIFT_CERTIFICATE.getErrorCode());
        }
    }

    /**
     * Validates gift certificate tag name
     *
     * @param name the gift certificate tag name
     */
    public void validateTagName(String name) {
        if (StringUtils.isBlank(name) || name.length() > ValidValue.MAX_LENGTH_NAME) {
            throw new IncorrectParamValueException("invalid tag name = " + name,
                    MessageKey.INCORRECT_NAME, name, ErrorCode.GIFT_CERTIFICATE.getErrorCode());
        }
    }
}