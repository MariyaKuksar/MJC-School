package com.epam.esm.service;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.UserDto;

public interface UserService {
    UserDto findUserById(long id);

    PageDto<UserDto> findAllUsers(PaginationDto paginationDto);
}
