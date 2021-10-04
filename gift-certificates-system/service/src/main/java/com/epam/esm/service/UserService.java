package com.epam.esm.service;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.UserDto;

/**
 * The interface for operations with users
 *
 * @author Maryia_Kuksar
 * @version 1.0
 */
public interface UserService {
    /**
     * Finds user by id
     *
     * @param id the user id which needs to find
     * @return the found user dto
     */
    UserDto findUserById(long id);

    /**
     * Finds all users
     *
     * @param paginationDto the data for pagination
     * @return the page with all users and total number of positions
     */
    PageDto<UserDto> findAllUsers(PaginationDto paginationDto);
}
