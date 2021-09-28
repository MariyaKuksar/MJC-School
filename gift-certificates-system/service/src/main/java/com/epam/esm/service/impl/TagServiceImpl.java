package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ErrorDetails;
import com.epam.esm.exception.MessageKey;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class is implementation of interface {@link TagService}
 * for operations with tags.
 *
 * @author Maryia Kuksar
 * @version 1.0
 * @see TagService
 */
@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final ModelMapper modelMapper;
    private final TagValidator tagValidator;

    @Autowired
    public TagServiceImpl(TagDao tagDao, ModelMapper modelMapper, TagValidator tagValidator) {
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
        this.tagValidator = tagValidator;
    }
    @Transactional
    @Override
    public TagDto createTag(TagDto tagDto) {
        String tagName = tagDto.getName();
        tagValidator.validateName(tagName);
        if (tagDao.findByName(tagName).isPresent()) {
            throw new ResourceAlreadyExistsException("such name already exist, invalid name = " + tagName,
                    new ErrorDetails(MessageKey.NAME_ALREADY_EXIST, tagName, ErrorCode.TAG_INVALID_NAME.getErrorCode()));
        }
        Tag tag = modelMapper.map(tagDto, Tag.class);
        tag = tagDao.create(tag);
        return modelMapper.map(tag, TagDto.class);
    }

    @Override
    public TagDto findTagById(long id) {
        tagValidator.validateId(id);
        Optional<Tag> tagOptional = tagDao.findById(id);
        return tagOptional.map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("invalid id = " + id,
                        new ErrorDetails(MessageKey.RESOURCE_NOT_FOUND_BY_ID, String.valueOf(id), ErrorCode.TAG_INVALID_ID.getErrorCode())));
    }

    @Override
    public PageDto<TagDto> findAllTags(PaginationDto paginationDto) {
        Pagination pagination = modelMapper.map(paginationDto, Pagination.class);
        List<Tag> tagList = tagDao.findAll(pagination);
        List<TagDto> tagDtoList = tagList.stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
        long totalNumberPosition = tagDao.getTotalNumber();
        return new PageDto<>(tagDtoList, totalNumberPosition);
    }

    @Transactional
    @Override
    public void deleteTag(long id) {
        tagValidator.validateId(id);
        if (!tagDao.delete(id)) {
            throw new ResourceNotFoundException("invalid id = " + id,
                    new ErrorDetails(MessageKey.RESOURCE_NOT_FOUND_BY_ID, String.valueOf(id), ErrorCode.TAG_INVALID_ID.getErrorCode()));
        }
    }
}
