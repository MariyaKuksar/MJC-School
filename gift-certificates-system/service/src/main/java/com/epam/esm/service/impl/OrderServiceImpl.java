package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ErrorDetails;
import com.epam.esm.exception.MessageKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.OrderValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final ModelMapper modelMapper;
    private final OrderValidator orderValidator;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, ModelMapper modelMapper, OrderValidator orderValidator) {
        this.orderDao = orderDao;
        this.modelMapper = modelMapper;
        this.orderValidator = orderValidator;
    }

    @Override
    public OrderDto findOrderById(long id) {
        orderValidator.validateId(id);
        Optional<Order> orderOptional = orderDao.findById(id);
        return orderOptional.map(order -> modelMapper.map(order, OrderDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("invalid id = " + id,
                new ErrorDetails(MessageKey.RESOURCE_NOT_FOUND_BY_ID, String.valueOf(id), ErrorCode.ORDER_INVALID_ID.getErrorCode())));
    }
}
