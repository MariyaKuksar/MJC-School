package com.epam.esm.entity.audit;

import com.epam.esm.entity.Order;

import javax.persistence.PrePersist;
import java.time.ZonedDateTime;

public class OrderAudit {

    @PrePersist
    public void beforeCreateOrder(Order order) {
        order.setCreateDate(ZonedDateTime.now());
    }
}
