package com.epam.esm.controller;

import static  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.converter.ParamsToDtoConverter;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ParamsToDtoConverter paramsToDtoConverter;

    @Autowired
    public UserController(UserService userService, ParamsToDtoConverter paramsToDtoConverter) {
        this.userService = userService;
        this.paramsToDtoConverter = paramsToDtoConverter;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable long id) {
        UserDto userDto = userService.findUserById(id);
        addLinks(userDto);
        return userDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDto<UserDto> getUsers(@RequestParam Map<String, String> pageParams) {
        PaginationDto paginationDto = paramsToDtoConverter.getPaginationDto(pageParams);
        PageDto<UserDto> pageDto = userService.findAllUsers(paginationDto);
        pageDto.getPagePositions().forEach(this::addLinks);
        return pageDto;
    }

    private void addLinks(UserDto userDto) {
        userDto.add(linkTo(methodOn(UserController.class).getUserById(userDto.getId())).withSelfRel());
    }
}
