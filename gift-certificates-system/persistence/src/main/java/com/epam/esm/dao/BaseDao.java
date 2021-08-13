package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    T create(T entity);

    List<T> findAll();

    Optional<T> findById(long id);

    boolean update(T entity);

    boolean delete(long id);
}
