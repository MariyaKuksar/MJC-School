package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ErrorDetails;
import com.epam.esm.exception.MessageKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class is implementation of interface {@link UserService}
 * for operations with users.
 *
 * @author Maryia_Kuksar
 * @version 1.0
 * @see UserService
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final UserValidator userValidator;

    @Autowired
    public UserServiceImpl(UserDao userDao, ModelMapper modelMapper, UserValidator userValidator) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
    }

    @Override
    public UserDto findUserById(long id) {
        userValidator.validateId(id);
        Optional<User> userOptional = userDao.findById(id);
        return userOptional.map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("invalid id = " + id,
                        new ErrorDetails(MessageKey.RESOURCE_NOT_FOUND_BY_ID, String.valueOf(id), ErrorCode.USER_INVALID_ID.getErrorCode())));
    }

    @Override
    public PageDto<UserDto> findAllUsers(PaginationDto paginationDto) {
        Pagination pagination = modelMapper.map(paginationDto, Pagination.class);
        List<User> userList = userDao.findAll(pagination);
        List<UserDto> userDtoList = userList.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        long totalNumberPosition = userDao.getTotalNumber();
        return new PageDto<>(userDtoList, totalNumberPosition);
    }
}
