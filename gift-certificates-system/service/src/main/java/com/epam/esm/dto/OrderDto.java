package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Class is implementation of pattern DTO for transmission order
 * entity between service and controller.
 *
 * @author Maryia_Kuksar
 * @version 1.0
 * @see RepresentationModel
 */
public class OrderDto extends RepresentationModel<OrderDto> {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal cost;
    private UserDto user;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime createDate;
    private List <OrderedGiftCertificateDto> orderedGiftCertificates;

    public OrderDto() {
    }

    public OrderDto(long id, BigDecimal cost, UserDto user, ZonedDateTime createDate, List<OrderedGiftCertificateDto> orderedGiftCertificates) {
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public List<OrderedGiftCertificateDto> getOrderedGiftCertificates() {
        return orderedGiftCertificates;
    }

    public void setOrderedGiftCertificates(List<OrderedGiftCertificateDto> orderedGiftCertificates) {
        this.orderedGiftCertificates = orderedGiftCertificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderDto orderDto = (OrderDto) o;

        if (id != orderDto.id) return false;
        if (cost != null ? !cost.equals(orderDto.cost) : orderDto.cost != null) return false;
        if (user != null ? !user.equals(orderDto.user) : orderDto.user != null) return false;
        if (createDate != null ? !createDate.equals(orderDto.createDate) : orderDto.createDate != null) return false;
        return orderedGiftCertificates != null ? orderedGiftCertificates.equals(orderDto.orderedGiftCertificates) : orderDto.orderedGiftCertificates == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (orderedGiftCertificates != null ? orderedGiftCertificates.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderDto{");
        sb.append("id=").append(id);
        sb.append(", cost=").append(cost);
        sb.append(", user=").append(user);
        sb.append(", createDate=").append(createDate);
        sb.append(", orderedGiftCertificates=").append(orderedGiftCertificates);
        sb.append('}');
        return sb.toString();
    }
}
