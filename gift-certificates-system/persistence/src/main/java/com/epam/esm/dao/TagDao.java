package com.epam.esm.dao;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface for working with tag table in database.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
public interface TagDao {

    /**
     * Creates new tag in database
     *
     * @param tag the tag for creating
     * @return the created tag
     */
    Tag create(Tag tag);

    /**
     * Finds all tags in database
     *
     * @return the list of all founded tags
     */
    List<Tag> findAll(Pagination pagination);

    /**
     * Finds tag in database by id
     *
     * @param id the tag id which needs to found
     * @return the founded tag
     */
    Optional<Tag> findById(long id);

    /**
     * Finds tag in database by name
     *
     * @param name the tag name which needs to found
     * @return the founded tag
     */
    Optional<Tag> findByName(String name);

    /**
     * Deletes tag in database
     *
     * @param id the tag id which needs to delete
     * @return true if tag was deleted, else false
     */
    boolean delete(long id);

    long  getTotalNumber();
}
