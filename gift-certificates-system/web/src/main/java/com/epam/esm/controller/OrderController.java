package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    @ResponseStatus
    public OrderDto getOrderById(@PathVariable long id) {
        OrderDto orderDto = orderService.findOrderById(id);
        addLinks(orderDto);
        return orderDto;
    }

    private void addLinks(OrderDto orderDto) {
        orderDto.add(linkTo(methodOn(OrderController.class).getOrderById(orderDto.getId())).withSelfRel());
        orderDto.getUser().add(linkTo(methodOn(UserController.class).getUserById(orderDto.getUser().getId())).withSelfRel());
        orderDto.getOrderedGiftCertificates().forEach(orderedGiftCertificateDto ->
                orderedGiftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificateById(orderedGiftCertificateDto.getGiftCertificateId())).withSelfRel()));
    }
}
