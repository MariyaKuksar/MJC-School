package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;

import java.util.List;
import java.util.Optional;

/**
 * The interface for working with order table in database.
 *
 * @author Maryia_Kuksar
 * @version 1.0
 */
public interface OrderDao {

    /**
     * Creates new order in database
     *
     * @param order the order for creating
     * @return the created order
     */
    Order create(Order order);

    /**
     * Finds order in database by id
     *
     * @param id the order id which needs to find
     * @return the found order
     */
    Optional<Order> findById(long id);

    /**
     * Finds orders in database by user id
     *
     * @param userId     the user id whose orders need to find
     * @param pagination the data for pagination
     * @return the list of found orders
     */
    List<Order> findByUserId(long userId, Pagination pagination);

    /**
     * Gets the total number of orders by search params
     *
     * @param userId the user id whose total number of orders need to get
     * @return the total number of orders by user id
     */
    long getTotalNumberByUserId(long userId);
}
