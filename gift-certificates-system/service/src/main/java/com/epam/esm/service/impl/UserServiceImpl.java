package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.Status;
import com.epam.esm.entity.User;
import com.epam.esm.exception.*;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, ModelMapper modelMapper, UserValidator userValidator, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {
        userValidator.validateUser(userDto);
        checkIfEmailFree(userDto.getEmail());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRole(Role.USER);
        userDto.setStatus(Status.ACTIVE);
        User user = modelMapper.map(userDto, User.class);
        user = userDao.createUser(user);
        return modelMapper.map(user, UserDto.class);
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

    private void checkIfEmailFree(String email) {
        if (userDao.findByEmail(email).isPresent()) {
            throw new ResourceAlreadyExistsException("user with such email already exist, invalid email = " + email,
                    new ErrorDetails(MessageKey.EMAIL_ALREADY_EXISTS,
                            email, ErrorCode.USER_INVALID_EMAIL.getErrorCode()));
        }
    }

}
