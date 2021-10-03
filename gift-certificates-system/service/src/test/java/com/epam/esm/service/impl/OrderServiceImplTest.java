package com.epam.esm.service.impl;

import com.epam.esm.configuration.ServiceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderedGiftCertificateDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.PaginationDto;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import com.epam.esm.entity.OrderedGiftCertificate;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.OrderValidator;
import com.epam.esm.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ServiceConfiguration.class)
public class OrderServiceImplTest {
    @MockBean
    private OrderDao orderDao;
    @MockBean
    private OrderValidator orderValidator;
    @MockBean
    private UserDao userDao;
    @MockBean
    private GiftCertificateDao giftCertificateDao;
    @MockBean
    private UserValidator userValidator;
    @Autowired
    private OrderService orderService;
    private User user;
    private GiftCertificate giftCertificate;
    private Order order;
    private OrderDto orderDto1;
    private OrderDto orderDto2;
    PageDto<OrderDto> pageDto;


    @BeforeEach
    public void setUp() {
        user = new User(2, "Alexandra");
        giftCertificate = new GiftCertificate(10, "New Year holiday", "gift", new BigDecimal("100"), 100,
                ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"),
                null);
        order = new Order(1, new BigDecimal("100"), user, ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), Arrays.asList(new OrderedGiftCertificate(10, 1, "New Year holiday", "gift", new BigDecimal("100"), 100,
                ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), 1)));
        orderDto1 = new OrderDto(0, null, new UserDto(2, null), null, Arrays.asList(new OrderedGiftCertificateDto(10, 1)));
        orderDto2 = new OrderDto(1, new BigDecimal("100"), new UserDto(2, "Alexandra"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), Arrays.asList(new OrderedGiftCertificateDto(10, 1, "New Year holiday", "gift", new BigDecimal("100"), 100,
                ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), 1)));
        pageDto = new PageDto<>(Arrays.asList(orderDto2), 1);
    }

    @Test
    public void createOrderPositiveTest() {
        doNothing().when(orderValidator).validateOrder(isA(OrderDto.class));
        when(userDao.findById(anyLong())).thenReturn(Optional.of(user));
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate));
        when(orderDao.create(isA(Order.class))).thenReturn(order);
        OrderDto actual = orderService.createOrder(orderDto1);
        assertEquals(orderDto2, actual);
    }

    @Test
    public void createOrderNegativeTest() {
        doThrow(IncorrectParamValueException.class).when(orderValidator).validateOrder(isA(OrderDto.class));
        assertThrows(IncorrectParamValueException.class, () -> orderService.createOrder(orderDto1));
    }

    @Test
    public void findOrderByIdPositiveTest() {
        doNothing().when(orderValidator).validateId(anyLong());
        when(orderDao.findById(anyLong())).thenReturn(Optional.of(order));
        OrderDto actual = orderService.findOrderById(1);
        assertEquals(orderDto2, actual);
    }

    @Test
    public void findOrderByIdNegativeTest() {
        doNothing().when(orderValidator).validateId(anyLong());
        when(orderDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> orderService.findOrderById(15));
    }

    @Test
    public void findOrderByUserIdPositiveTest() {
        doNothing().when(userValidator).validateId(anyLong());
        when(orderDao.findByUserId(anyLong(), isA(Pagination.class))).thenReturn(Arrays.asList(order));
        when(orderDao.getTotalNumberByUserId(anyLong())).thenReturn(1L);
        PageDto<OrderDto> actual = orderService.findOrderByUserId(10, new PaginationDto(1, 1));
        assertEquals(pageDto, actual);
    }

    @Test
    public void findOrderByUserIdNegativeTest() {
        doThrow(IncorrectParamValueException.class).when(userValidator).validateId(anyLong());
        assertThrows(IncorrectParamValueException.class, () -> orderService.findOrderByUserId(0, new PaginationDto(1, 1)));
    }
}
