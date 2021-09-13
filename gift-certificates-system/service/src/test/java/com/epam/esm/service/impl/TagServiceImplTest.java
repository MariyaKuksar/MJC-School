package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TagServiceImplTest {
    private TagDao tagDao;
    private ModelMapper modelMapper;
    private TagValidator tagValidator;
    private TagService tagService;
    TagDto tagDto1;
    Tag tag1;
    TagDto tagDto2;

    @BeforeEach
    public void setUp() {
        tagDao = mock(TagDao.class);
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STRICT).setFieldMatchingEnabled(true).setSkipNullEnabled(true);
        tagValidator = mock(TagValidator.class);
        tagService = new TagServiceImpl(tagDao, modelMapper, tagValidator);
        tagDto1 = new TagDto(0, "work");
        tag1 = new Tag(11, "work");
        tagDto2 = new TagDto(11, "work");
    }

    @Test
    public void createTagPositiveTest() {
        doNothing().when(tagValidator).validateName(anyString());
        when(tagDao.findByName(anyString())).thenReturn(Optional.empty());
        when(tagDao.create(isA(Tag.class))).thenReturn(tag1);
        TagDto actual = tagService.createTag(tagDto1);
        assertEquals(tagDto2, actual);
    }

    @Test
    public void createTagNegativeTest() {
        doNothing().when(tagValidator).validateName(anyString());
        when(tagDao.findByName(anyString())).thenReturn(Optional.of(tag1));
        assertThrows(IncorrectParamValueException.class, () -> tagService.createTag(tagDto1));
    }

    @Test
    public void findTagByIdPositiveTest() {
        doNothing().when(tagValidator).validateId(anyLong());
        when(tagDao.findById(anyLong())).thenReturn(Optional.of(tag1));
        TagDto actual = tagService.findTagById(11);
        assertEquals(tagDto2, actual);
    }

    @Test
    public void findTagByIdNegativeTest() {
        doThrow(IncorrectParamValueException.class).when(tagValidator).validateId(anyLong());
        assertThrows(IncorrectParamValueException.class, () -> tagService.findTagById(0));
    }

    @Test
    public void findAllTagsPositiveTest() {
        when(tagDao.findAll()).thenReturn(Arrays.asList(tag1));
        List<TagDto> actual = tagService.findAllTags();
        assertEquals(1, actual.size());
    }

    @Test
    public void deleteTagPositiveTest() {
        doNothing().when(tagValidator).validateId(anyLong());
        when(tagDao.delete(anyLong())).thenReturn(true);
        tagService.deleteTag(11);
        verify(tagDao, times(1)).delete(anyLong());
    }

    @Test
    public void deleteTagNegativeTest() {
        doNothing().when(tagValidator).validateId(anyLong());
        when(tagDao.delete(anyLong())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> tagService.deleteTag(1000));
    }
}