package com.epam.esm.service.impl;

import com.epam.esm.configuration.ServiceConfiguration;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ServiceConfiguration.class)
public class TagServiceImplTest {
    @MockBean
    private TagDao tagDao;
    @MockBean
    private TagValidator tagValidator;
    @Autowired
    private TagService tagService;
    TagDto tagDto1;
    Tag tag1;
    TagDto tagDto2;

    @BeforeEach
    public void setUp() {
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
        assertThrows(ResourceAlreadyExistsException.class, () -> tagService.createTag(tagDto1));
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
    public void findMostPopularTagOfUserWithHighestCostOfAllOrdersPositiveTest() {
        when(tagDao.findMostPopularTagOfUserWithHighestCostOfAllOrders()).thenReturn(Optional.of(tag1));
        TagDto actual = tagService.findMostPopularTagOfUserWithHighestCostOfAllOrders();
        assertEquals(tagDto2, actual);
    }

    @Test
    public void findMostPopularTagOfUserWithHighestCostOfAllOrdersNegativeTest() {
        when(tagDao.findMostPopularTagOfUserWithHighestCostOfAllOrders()).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> tagService.findMostPopularTagOfUserWithHighestCostOfAllOrders());
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