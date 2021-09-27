package com.epam.esm.controller;

import com.epam.esm.converter.ParamsToDtoConverter;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
        return userService.findUserById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDto<UserDto> getUsers(@RequestParam Map<String, String> pageParams) {
        PaginationDto paginationDto = paramsToDtoConverter.getPaginationDto(pageParams);
        PageDto<UserDto> page = userService.findAllUsers(paginationDto);
        page.getPagePositions().forEach(this::addLinks);
        return page;
    }

    private void addLinks(UserDto userDto) {
        userDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(userDto.getId())).withSelfRel());
    }
}
