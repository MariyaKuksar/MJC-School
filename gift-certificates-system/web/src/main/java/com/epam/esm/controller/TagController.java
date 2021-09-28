package com.epam.esm.controller;

import static  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.converter.ParamsToDtoConverter;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Class is an endpoint of the API which performs CRUD operations on tags.
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
        TagDto tagDto = tagService.findTagById(id);
        addLinks(tagDto);
        return tagDto;
    }

    /**
     * Gets all tags, processes GET requests at /tags
     *
     * @return the list of all tags dto
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
     * Deletes tag by id, processes DELETE requests at /tags/{id}
     *
     * @param id the tag id which needs to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteTag(@PathVariable long id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void addLinks (TagDto tagDto){
        tagDto.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel());
        tagDto.add(linkTo(methodOn(TagController.class).deleteTag(tagDto.getId())).withRel(DELETE));
    }
}
