package com.epam.esm.entity;

import java.io.Serializable;

public class OrderedGiftCertificateId implements Serializable {
    private long giftCertificateId;
    private long orderId;

    public OrderedGiftCertificateId() {
    }

    public OrderedGiftCertificateId(long giftCertificateId, long orderId) {
        this.giftCertificateId = giftCertificateId;
        this.orderId = orderId;
    }

    public long getGiftCertificateId() {
        return giftCertificateId;
    }

    public void setGiftCertificateId(long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedGiftCertificateId that = (OrderedGiftCertificateId) o;

        if (giftCertificateId != that.giftCertificateId) return false;
        return orderId == that.orderId;
    }

    @Override
    public int hashCode() {
        int result = (int) (giftCertificateId ^ (giftCertificateId >>> 32));
        result = 31 * result + (int) (orderId ^ (orderId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderedGiftCertificateId{");
        sb.append("giftCertificateId=").append(giftCertificateId);
        sb.append(", orderId=").append(orderId);
        sb.append('}');
        return sb.toString();
    }
}
