package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/**
 * Class is an endpoint of the API which performs CRUD operations on tags.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Creates new tag, processes POST requests at /tags
     *
     * @param tagDto data for creating new tag
     * @return the created tag dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(@RequestBody TagDto tagDto) {
        return tagService.createTag(tagDto);
    }

    /**
     * Gets tag by id, processes GET requests at /tag/{id}
     *
     * @param id the tag id which needs to found
     * @return the founded tag dto
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getTagById(@PathVariable long id) {
        return tagService.findTagById(id);
    }

    /**
     * Gets all tags, processes GET requests at /tags
     *
     * @return the list of all tags dto
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getTags() {
        return tagService.findAllTags();
    }

    /**
     * Deletes tag by id, processes DELETE requests at /tags/{id}
     *
     * @param id the tag id which needs to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
        tagService.deleteTag(id);
    }
}
