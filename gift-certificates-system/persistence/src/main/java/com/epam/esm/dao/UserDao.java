package com.epam.esm.dao;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface for working with user table in database.
 *
 * @author Maryia_Kuksar
 * @version 1.0
 */
public interface UserDao {

    User createUser(User user);

    /**
     * Finds user by id
     *
     * @param id the user id
     * @return the found user
     */
    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    /**
     * Finds all users
     *
     * @param pagination the data for pagination
     * @return the all users
     */
    List<User> findAll(Pagination pagination);

    /**
     * Gets the total number of users
     *
     * @return the total number of
     */
    long getTotalNumber();

}
