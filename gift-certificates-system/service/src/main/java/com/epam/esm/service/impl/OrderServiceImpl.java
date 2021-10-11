package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderedGiftCertificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ErrorDetails;
import com.epam.esm.exception.MessageKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.OrderValidator;
import com.epam.esm.validator.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class is implementation of interface {@link OrderService}
 * for operations with orders
 *
 * @author Maryia_Kuksar
 * @version 1.0
 * @see OrderService
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final ModelMapper modelMapper;
    private final OrderValidator orderValidator;
    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;
    private final UserValidator userValidator;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, ModelMapper modelMapper, OrderValidator orderValidator, UserDao userDao, GiftCertificateDao giftCertificateDao, UserValidator userValidator) {
        this.orderDao = orderDao;
        this.modelMapper = modelMapper;
        this.orderValidator = orderValidator;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
        this.userValidator = userValidator;
    }

    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderValidator.validateOrder(orderDto);
        Order order = modelMapper.map(orderDto, Order.class);
        order.setUser(userDao.findById(orderDto.getUser().getId())
                .orElseThrow(() -> new IncorrectParamValueException("invalid id" + orderDto.getUser().getId(),
                        Arrays.asList(new ErrorDetails(MessageKey.NO_SUCH_USER_EXISTS, String.valueOf(orderDto.getUser().getId()),
                                ErrorCode.USER_INVALID_ID.getErrorCode())))));
        order.getOrderedGiftCertificates().forEach(this::setOrderedGiftCertificateDetails);
        setOrderCost(order);
        order = orderDao.create(order);
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public OrderDto findOrderById(long id) {
        orderValidator.validateId(id);
        Optional<Order> orderOptional = orderDao.findById(id);
        return orderOptional.map(order -> modelMapper.map(order, OrderDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("invalid id = " + id,
                        new ErrorDetails(MessageKey.RESOURCE_NOT_FOUND_BY_ID, String.valueOf(id), ErrorCode.ORDER_INVALID_ID.getErrorCode())));
    }

    @Override
    public PageDto<OrderDto> findOrderByUserId(long userId, PaginationDto paginationDto) {
        userValidator.validateId(userId);
        Pagination pagination = modelMapper.map(paginationDto, Pagination.class);
        userDao.findById(userId).orElseThrow(() -> new IncorrectParamValueException("invalid id" + userId,
                Arrays.asList(new ErrorDetails(MessageKey.NO_SUCH_USER_EXISTS, String.valueOf(userId), ErrorCode.USER_INVALID_ID.getErrorCode()))));
        List<Order> orderList = orderDao.findByUserId(userId, pagination);
        List<OrderDto> orderDtoList = orderList.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
        long totalNumberPositions = orderDao.getTotalNumberByUserId(userId);
        return new PageDto<>(orderDtoList, totalNumberPositions);
    }

    private void setOrderedGiftCertificateDetails(OrderedGiftCertificate orderedGiftCertificate) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(orderedGiftCertificate.getGiftCertificateId())
                .orElseThrow(() -> new IncorrectParamValueException("invalid id" + orderedGiftCertificate.getGiftCertificateId(),
                        Arrays.asList(new ErrorDetails(MessageKey.NO_SUCH_GIFT_CERTIFICATE_EXISTS, String.valueOf(orderedGiftCertificate.getGiftCertificateId()), ErrorCode.GIFT_CERTIFICATE_INVALID_ID.getErrorCode()))));
        orderedGiftCertificate.setName(giftCertificate.getName());
        orderedGiftCertificate.setDescription(giftCertificate.getDescription());
        orderedGiftCertificate.setPrice(giftCertificate.getPrice());
        orderedGiftCertificate.setDuration(giftCertificate.getDuration());
        orderedGiftCertificate.setCreateDate(giftCertificate.getCreateDate());
        orderedGiftCertificate.setLastUpdateDate(giftCertificate.getLastUpdateDate());
    }

    private void setOrderCost(Order order) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (OrderedGiftCertificate orderedGiftCertificate : order.getOrderedGiftCertificates()) {
            BigDecimal giftCertificateCost = orderedGiftCertificate.getPrice().multiply(BigDecimal.valueOf(orderedGiftCertificate.getQuantity()));
            totalCost = totalCost.add(giftCertificateCost);
        }
        order.setCost(totalCost);
    }
}
