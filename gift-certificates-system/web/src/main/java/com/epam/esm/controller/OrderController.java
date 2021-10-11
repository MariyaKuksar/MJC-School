package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.converter.ParamsToDtoConverter;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Class is an endpoint of the API which performs operations on orders.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ParamsToDtoConverter paramsToDtoConverter;

    @Autowired
    public OrderController(OrderService orderService, ParamsToDtoConverter paramsToDtoConverter) {
        this.orderService = orderService;
        this.paramsToDtoConverter = paramsToDtoConverter;
    }

    /**
     * Creates new order, processes POST requests at /orders
     *
     * @param orderDto data for creating new order
     * @return the created order dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('make_order')")
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        OrderDto createdOrderDto = orderService.createOrder(orderDto);
        addLinks(createdOrderDto);
        return createdOrderDto;
    }

    /**
     * Gets order by id, processes GET requests at /orders/{id}
     *
     * @param id the order id which needs to find
     * @return the found order dto
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('read')")
    public OrderDto getOrderById(@PathVariable long id) {
        OrderDto orderDto = orderService.findOrderById(id);
        addLinks(orderDto);
        return orderDto;
    }

    /**
     * Gets order by user id, processes GET requests at /orders/users/{userId}
     *
     * @param userId     the user id whose orders need to find
     * @param pageParams the data for pagination
     * @return the page with found orders and total number of positions
     */
    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('read')")
    public PageDto<OrderDto> getOrdersByUserId(@PathVariable long userId, @RequestParam Map<String, String> pageParams) {
        PaginationDto paginationDto = paramsToDtoConverter.getPaginationDto(pageParams);
        PageDto<OrderDto> pageDto = orderService.findOrderByUserId(userId, paginationDto);
        pageDto.getPagePositions().forEach(this::addLinks);
        return pageDto;
    }

    private void addLinks(OrderDto orderDto) {
        orderDto.add(linkTo(methodOn(OrderController.class)
                .getOrderById(orderDto.getId()))
                .withSelfRel());
        orderDto.getUser().add(linkTo(methodOn(UserController.class)
                .getUserById(orderDto.getUser().getId()))
                .withSelfRel());
        orderDto.getOrderedGiftCertificates().forEach(orderedGiftCertificateDto ->
                orderedGiftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class)
                        .getGiftCertificateById(orderedGiftCertificateDto.getGiftCertificateId()))
                        .withSelfRel()));
    }
}
