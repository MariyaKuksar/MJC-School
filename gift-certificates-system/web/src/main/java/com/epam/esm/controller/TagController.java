package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.converter.ParamsToDtoConverter;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Map;

/**
 * Class is an endpoint of the API which performs operations on tags.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    private static final String DELETE = "delete";
    private final TagService tagService;
    private final ParamsToDtoConverter paramsToDtoConverter;

    @Autowired
    public TagController(TagService tagService, ParamsToDtoConverter paramsToDtoConverter) {
        this.tagService = tagService;
        this.paramsToDtoConverter = paramsToDtoConverter;
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
        TagDto createdTagDto = tagService.createTag(tagDto);
        addLinks(tagDto);
        return createdTagDto;
    }

    /**
     * Gets tag by id, processes GET requests at /tags/{id}
     *
     * @param id the tag id which needs to find
     * @return the found tag dto
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getTagById(@PathVariable long id) {
        TagDto tagDto = tagService.findTagById(id);
        addLinks(tagDto);
        return tagDto;
    }

    /**
     * Gets all tags, processes GET requests at /tags
     *
     * @param pageParams the data for pagination
     * @return the page with all tags and total number of positions
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDto<TagDto> getTags(@RequestParam Map<String, String> pageParams) {
        PaginationDto paginationDto = paramsToDtoConverter.getPaginationDto(pageParams);
        PageDto<TagDto> pageDto = tagService.findAllTags(paginationDto);
        pageDto.getPagePositions().forEach(this::addLinks);
        return pageDto;
    }

    /**
     * Gets the most popular tag of the user with the highest cost of all orders,
     * processes GET requests at /tags/popular
     *
     * @return the found tag dto
     */
    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getMostPopularTagOfUserWithHighestCostOfAllOrders() {
        TagDto tagDto = tagService.findMostPopularTagOfUserWithHighestCostOfAllOrders();
        addLinks(tagDto);
        return tagDto;
    }

    /**
     * Deletes tag by id, processes DELETE requests at /tags/{id}
     *
     * @param id the tag id which needs to delete
     * @return void
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTag(@PathVariable long id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void addLinks(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel());
        tagDto.add(linkTo(methodOn(TagController.class).deleteTag(tagDto.getId())).withRel(DELETE));
    }
}
