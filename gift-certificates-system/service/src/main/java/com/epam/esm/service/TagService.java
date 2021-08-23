package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService {

    TagDto createTag(TagDto tagDto);

    TagDto findTagById(long id);

    List<TagDto> findAllTags();

    void deleteTag(long id);
}

