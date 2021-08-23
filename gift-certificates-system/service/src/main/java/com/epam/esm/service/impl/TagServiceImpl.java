package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.MessageKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final ModelMapper modelMapper;

    @Autowired
    public TagServiceImpl(TagDao tagDao, ModelMapper modelMapper) {
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        String tagName = tagDto.getName();
        TagValidator.validateName(tagName);
        if (tagDao.findByName(tagName).isPresent()) {
            throw new IncorrectParamValueException("such name already exist, invalid name = " + tagName,
                    MessageKey.NAME_ALREADY_EXIST, tagName, ErrorCode.TAG.getErrorCode());
        }
        Tag tag = modelMapper.map(tagDto, Tag.class);
        tag = tagDao.create(tag);
        tagDto.setId(tag.getId());
        return tagDto;
    }

    @Override
    public TagDto findTagById(long id) {
        TagValidator.validateId(id);
        Optional<Tag> tagOptional = tagDao.findById(id);
        return tagOptional.map(tag -> modelMapper.map(tag, TagDto.class)).orElseThrow(() -> new ResourceNotFoundException("invalid id = " + id, MessageKey.RESOURCE_NOT_FOUND_BY_ID, String.valueOf(id), ErrorCode.TAG.getErrorCode()));
    }

    @Override
    public List<TagDto> findAllTags() {
        List<Tag> tagList = tagDao.findAll();
        return tagList.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteTag(long id) {
        TagValidator.validateId(id);
        if (!tagDao.delete(id)) {
            throw new ResourceNotFoundException("invalid id = " + id, MessageKey.RESOURCE_NOT_FOUND_BY_ID,
                    String.valueOf(id), ErrorCode.TAG.getErrorCode());
        }
    }
}
