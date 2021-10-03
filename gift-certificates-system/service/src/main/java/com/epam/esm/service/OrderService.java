package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);

    OrderDto findOrderById(long id);

    PageDto<OrderDto> findOrderByUserId(long userId, PaginationDto paginationDto);
}
