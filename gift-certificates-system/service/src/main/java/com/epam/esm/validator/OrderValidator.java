package com.epam.esm.validator;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderedGiftCertificateDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ErrorDetails;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.MessageKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class provides methods to validate fields of {@link OrderDto}
 *
 * @author Maryia_Kuksar
 * @version 1.0
 */
@Component
public class OrderValidator {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Validates order id
     *
     * @param id the order id
     */
    public void validateId(long id) {
        if (id < ValidValue.MIN_ID) {
            ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_ID, String.valueOf(id),
                    ErrorCode.ORDER_INVALID_ID.getErrorCode());
            throw new IncorrectParamValueException("invalid id = " + id, Arrays.asList(errorDetails));
        }
    }

    /**
     * Validates all fields of order
     *
     * @param orderDto the data for validating
     */
    public void validateOrder(OrderDto orderDto) {
        List<ErrorDetails> errors = new ArrayList<>();
        validateUserId(orderDto.getUser().getId(), errors);
        orderDto.getOrderedGiftCertificates().forEach(orderedGiftCertificateDto ->
                validateOrderedGiftCertificatesId(orderedGiftCertificateDto.getGiftCertificateId(), errors));
        orderDto.getOrderedGiftCertificates().forEach(orderedGiftCertificateDto ->
                validateGiftCertificateQuantity(orderedGiftCertificateDto.getQuantity(), errors));
        if (!errors.isEmpty()) {
            throw new IncorrectParamValueException("invalid orders params", errors);
        }
        checkGiftCertificatesUniqueness(orderDto);
    }

    private void checkGiftCertificatesUniqueness(OrderDto orderDto) {
        List<OrderedGiftCertificateDto> orderedGiftCertificates = orderDto.getOrderedGiftCertificates();
        Set<OrderedGiftCertificateDto> uniqueGiftCertificates = new HashSet<>();
        for (OrderedGiftCertificateDto orderedGiftCertificate : orderedGiftCertificates) {
            long giftCertificateId = orderedGiftCertificate.getGiftCertificateId();
            List<OrderedGiftCertificateDto> sameGiftCertificates = orderedGiftCertificates.stream().filter(orderedGiftCertificateDto -> orderedGiftCertificateDto.getGiftCertificateId() == giftCertificateId).collect(Collectors.toList());
            if (sameGiftCertificates.size() > ValidValue.UNIQUE_QUANTITY) {
                int totalQuantity = 0;
                for (OrderedGiftCertificateDto giftCertificate : sameGiftCertificates) {
                    totalQuantity += giftCertificate.getQuantity();
                }
                uniqueGiftCertificates.add(new OrderedGiftCertificateDto(giftCertificateId, totalQuantity));
            } else {
                uniqueGiftCertificates.add(orderedGiftCertificate);
            }
        }
        orderDto.setOrderedGiftCertificates(new ArrayList<>(uniqueGiftCertificates));
    }

    private void validateGiftCertificateQuantity(int quantity, List<ErrorDetails> errors) {
        if (quantity < ValidValue.MIN_QUANTITY || quantity > ValidValue.MAX_QUANTITY) {
            ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_QUANTITY, String.valueOf(quantity),
                    ErrorCode.GIFT_CERTIFICATE_INVALID_QUANTITY.getErrorCode());
            errors.add(errorDetails);
            logger.error("gift certificate quantity error, invalid quantity = " + quantity);
        }
    }

    private void validateOrderedGiftCertificatesId(long giftCertificateId, List<ErrorDetails> errors) {
        if (giftCertificateId < ValidValue.MIN_ID) {
            ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_ID, String.valueOf(giftCertificateId),
                    ErrorCode.GIFT_CERTIFICATE_INVALID_ID.getErrorCode());
            errors.add(errorDetails);
            logger.error("gift certificate id error, invalid id = " + giftCertificateId);
        }
    }

    private void validateUserId(long userId, List<ErrorDetails> errors) {
        if (userId < ValidValue.MIN_ID) {
            ErrorDetails errorDetails = new ErrorDetails(MessageKey.INCORRECT_ID, String.valueOf(userId),
                    ErrorCode.USER_INVALID_ID.getErrorCode());
            errors.add(errorDetails);
            logger.error("user id error, invalid id = " + userId);
        }
    }
}
