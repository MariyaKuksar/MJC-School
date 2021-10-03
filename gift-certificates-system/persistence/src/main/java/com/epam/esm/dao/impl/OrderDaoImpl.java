package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {
    private static final String SELECT_ORDERS_BY_USER_ID = "FROM Order WHERE user.id=:userId";
    private static final String SELECT_TOTAL_NUMBER_USERS_ORDERS = "SELECT COUNT(*) FROM Order WHERE user.id=:userId";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order create(Order order) {
        entityManager.persist(order);
        order.getOrderedGiftCertificates().forEach(orderedGiftCertificate -> orderedGiftCertificate.setOrderId(order.getId()));
        order.getOrderedGiftCertificates().forEach(orderedGiftCertificate -> entityManager.persist(orderedGiftCertificate));
        return order;
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public List<Order> findByUserId(long userId, Pagination pagination) {
        return entityManager.createQuery(SELECT_ORDERS_BY_USER_ID, Order.class)
                .setParameter(QueryParam.USER_ID, userId)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .getResultList();
    }

    @Override
    public long getTotalNumberByUserId(long userId) {
        return (Long) entityManager.createQuery(SELECT_TOTAL_NUMBER_USERS_ORDERS)
                .setParameter(QueryParam.USER_ID, userId)
                .getSingleResult();
    }


}
