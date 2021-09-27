package com.epam.esm.dao;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll(Pagination pagination);

    Optional<User> findById(long id);

    long getTotalNumber();
}
