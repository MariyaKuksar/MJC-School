package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;

/**
 * The interface for operations with orders.
 *
 * @author Maryia_Kuksar
 * @version 1.0
 */
public interface OrderService {
    /**
     * Creates new order in database
     *
     * @param orderDto the data for creating new order
     * @return the created order dto
     */
    OrderDto createOrder(OrderDto orderDto);

    /**
     * Finds order by id
     *
     * @param id the order id which needs to find
     * @return the found order dto
     */
    OrderDto findOrderById(long id);

    /**
     * Finds orders by user id
     *
     * @param userId        the user id whose orders need to find
     * @param paginationDto the data for pagination
     * @return the page with found orders and total number of positions
     */
    PageDto<OrderDto> findOrderByUserId(long userId, PaginationDto paginationDto);
}
