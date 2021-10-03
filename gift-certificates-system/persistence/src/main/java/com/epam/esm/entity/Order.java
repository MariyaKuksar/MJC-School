package com.epam.esm.entity;

import com.epam.esm.entity.audit.OrderAudit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.EntityListeners;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "item_order")
@EntityListeners(OrderAudit.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "cost")
    private BigDecimal cost;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "create_date")
    private ZonedDateTime createDate;
    @OneToMany(mappedBy = "orderId")
    private List<OrderedGiftCertificate> orderedGiftCertificates;

    public Order() {
    }

    public Order(long id, BigDecimal cost, User user, ZonedDateTime createDate, List<OrderedGiftCertificate> orderedGiftCertificates) {
        this.id = id;
        this.cost = cost;
        this.user = user;
        this.createDate = createDate;
        this.orderedGiftCertificates = orderedGiftCertificates;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public List<OrderedGiftCertificate> getOrderedGiftCertificates() {
        return orderedGiftCertificates;
    }

    public void setOrderedGiftCertificates(List<OrderedGiftCertificate> orderedGiftCertificates) {
        this.orderedGiftCertificates = orderedGiftCertificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (cost != null ? !cost.equals(order.cost) : order.cost != null) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (createDate != null ? !createDate.equals(order.createDate) : order.createDate != null) return false;
        return orderedGiftCertificates != null ? orderedGiftCertificates.equals(order.orderedGiftCertificates) : order.orderedGiftCertificates == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (orderedGiftCertificates != null ? orderedGiftCertificates.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", cost=").append(cost);
        sb.append(", user=").append(user);
        sb.append(", createDate=").append(createDate);
        sb.append(", orderedGiftCertificates=").append(orderedGiftCertificates);
        sb.append('}');
        return sb.toString();
    }
}
