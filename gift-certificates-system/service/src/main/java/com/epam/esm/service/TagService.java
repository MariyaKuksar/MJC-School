package com.epam.esm.service;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
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
     *
     * @param id the tag id which needs to find
     * @return the found tag dto
     */
    TagDto findTagById(long id);

    /**
     * Finds all tags
     *
     * @param paginationDto the data for pagination
     * @return the page with all orders and total number of positions
     */
    PageDto<TagDto> findAllTags(PaginationDto paginationDto);

    /**
     * Finds the most popular tag of the user with the highest cost of all orders
     *
     * @return the found tag dto
     */
    TagDto findMostPopularTagOfUserWithHighestCostOfAllOrders();

    /**
     * Deletes tag by id
     *
     * @param id the tag id which needs to delete
     */
    void deleteTag(long id);
}

