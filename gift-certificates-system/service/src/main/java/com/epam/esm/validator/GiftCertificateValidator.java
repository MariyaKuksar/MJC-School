package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ErrorDetails;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.MessageKey;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Class provides methods to validate fields of {@link GiftCertificateDto}.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
@Component
public class GiftCertificateValidator {
    private static final Logger logger = LogManager.getLogger();
    /**
     * Validates all fields of gift certificates
     *
     * @param giftCertificateDto data for validating
     */
    public void validateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        List<ErrorDetails> errors = new ArrayList<>();
        validateName(giftCertificateDto.getName(), errors);
        validateDescription(giftCertificateDto.getDescription(), errors);
        validatePrice(giftCertificateDto.getPrice(), errors);
        validateDuration(giftCertificateDto.getDuration(), errors);
        List<TagDto> tagDtoList = giftCertificateDto.getTags();
        if (tagDtoList != null && !tagDtoList.isEmpty()) {
            tagDtoList.forEach(tagDto -> validateTagName(tagDto.getName(), errors));
        }
        if (!errors.isEmpty()) {
            throw new IncorrectParamValueException("invalid gift certificate params", errors);
        }
    }

    /**
     * Validates gift certificate id
     *
     * @param id the gift certificate id
     */
    public void validateId(long id) {
        if (id < ValidValue.MIN_ID) {
            ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_ID, String.valueOf(id),
                    ErrorCode.GIFT_CERTIFICATE_INVALID_ID.getErrorCode());
            throw new IncorrectParamValueException("invalid id = " + id, Arrays.asList(errorDetails));
        }
    }

    /**
     * Validates gift certificate name
     *
     * @param name the gift certificate name
     * @param errors list of errors for saving error
     */
    public void validateName(String name, List<ErrorDetails> errors) {
        if (StringUtils.isBlank(name)) {
            errors.add(new ErrorDetails(MessageKey.EMPTY_NAME, name,
                    ErrorCode.GIFT_CERTIFICATE_INVALID_NAME.getErrorCode()));
            logger.error("gift certificate name error, invalid name = " + name);
        } else if (name.length() > ValidValue.MAX_LENGTH_NAME) {
            errors.add(new ErrorDetails(MessageKey.INCORRECT_NAME, name,
                    ErrorCode.GIFT_CERTIFICATE_INVALID_NAME.getErrorCode()));
            logger.error("gift certificate name error, invalid name = " + name);
        }
    }

    /**
     * Validates gift certificate description
     *
     * @param description the gift certificate description
     * @param errors list of errors for saving error
     */
    public void validateDescription(String description, List<ErrorDetails> errors) {
        if (StringUtils.isBlank(description)) {
            errors.add(new ErrorDetails(MessageKey.EMPTY_DESCRIPTION, description,
                    ErrorCode.GIFT_CERTIFICATE_INVALID_DESCRIPTION.getErrorCode()));
            logger.error("gift certificate description error, invalid description = " + description);
        } else if (description.length() > ValidValue.MAX_LENGTH_DESCRIPTION) {
            errors.add(new ErrorDetails(MessageKey.INCORRECT_DESCRIPTION, description,
                    ErrorCode.GIFT_CERTIFICATE_INVALID_DESCRIPTION.getErrorCode()));
            logger.error("gift certificate description error, invalid description = " + description);
        }
    }

    /**
     * Validates gift certificate price
     *
     * @param price the gift certificate price
     * @param errors list of errors for saving error
     */
    public void validatePrice(BigDecimal price, List<ErrorDetails> errors) {
        if (price == null) {
            errors.add(new ErrorDetails(MessageKey.EMPTY_PRICE, String.valueOf(price),
                    ErrorCode.GIFT_CERTIFICATE_INVALID_PRICE.getErrorCode()));
            logger.error("gift certificate price error, invalid price = " + price);
        } else if (price.scale() > ValidValue.MAX_SCALE_PRICE || price.compareTo(ValidValue.MIN_PRICE) < 0 || price.compareTo(ValidValue.MAX_PRICE) > 0) {
            errors.add(new ErrorDetails(MessageKey.INCORRECT_PRICE, String.valueOf(price),
                    ErrorCode.GIFT_CERTIFICATE_INVALID_PRICE.getErrorCode()));
            logger.error("gift certificate price error, invalid price = " + price);
        }
    }

    /**
     * Validates gift certificate duration
     *
     * @param duration the gift certificate duration
     * @param errors list of errors for saving error
     */
    public void validateDuration(int duration, List<ErrorDetails> errors) {
        if (duration < ValidValue.MIN_DURATION || duration > ValidValue.MAX_DURATION) {
            errors.add(new ErrorDetails(MessageKey.INCORRECT_DURATION, String.valueOf(duration),
                    ErrorCode.GIFT_CERTIFICATE_INVALID_DURATION.getErrorCode()));
            logger.error("gift certificate duration error, invalid duration = " + duration);
        }
    }

    /**
     * Validates gift certificate tag name
     *
     * @param name the gift certificate tag name
     * @param errors list of errors for saving error
     */
    public void validateTagName(String name, List<ErrorDetails> errors) {
        if (StringUtils.isBlank(name)) {
            errors.add(new ErrorDetails(MessageKey.EMPTY_TAG_NAME, name, ErrorCode.TAG_INVALID_NAME.getErrorCode()));
            logger.error("gift certificate tag name error, invalid tag name = " + name);
        } else if (name.length() > ValidValue.MAX_LENGTH_NAME) {
            errors.add(new ErrorDetails(MessageKey.INCORRECT_TAG_NAME, name,
                    ErrorCode.TAG_INVALID_NAME.getErrorCode()));
            logger.error("gift certificate tag name error, invalid tag name = " + name);
        }
    }
}