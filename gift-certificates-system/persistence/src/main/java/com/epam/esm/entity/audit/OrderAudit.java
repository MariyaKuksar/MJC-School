package com.epam.esm.entity.audit;

import com.epam.esm.entity.Order;

import javax.persistence.PrePersist;
import java.time.ZonedDateTime;

/**
 * Class is listener for order entity.
 *
 * @author Maryia_Kuksar
 * @version 1.0
 */
public class OrderAudit {

    @PrePersist
    public void beforeCreateOrder(Order order) {
        order.setCreateDate(ZonedDateTime.now());
    }
}
