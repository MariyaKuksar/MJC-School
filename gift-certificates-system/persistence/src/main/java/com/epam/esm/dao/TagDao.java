package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {

    Tag create(Tag tag);

    List<Tag> findAll();

    Optional<Tag> findById(long id);

    Optional<Tag> findByName(String name);

    boolean delete(long id);
}
