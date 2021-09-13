package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

/**
 * The interface for operations with tags.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
public interface TagService {

    /**
     * Creates new tag
     *
     * @param tagDto data for creating new tag
     * @return the created tag dto
     */
    TagDto createTag(TagDto tagDto);

    /**
     * Finds tag by id
     * @param id the tag id which needs to found
     * @return the founded tag dto
     */
    TagDto findTagById(long id);

    /**
     * Finds all tags
     *
     * @return the list of all founded tags dto
     */
    List<TagDto> findAllTags();

    /**
     * Deletes tag by id
     *
     * @param id the tag id which needs to delete
     */
    void deleteTag(long id);
}

