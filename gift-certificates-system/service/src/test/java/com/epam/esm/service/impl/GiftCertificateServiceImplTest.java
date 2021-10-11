package com.epam.esm.service.impl;

import com.epam.esm.configuration.ServiceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParams;
import com.epam.esm.entity.Pagination;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@SpringBootTest (classes = ServiceConfiguration.class)
public class GiftCertificateServiceImplTest {
    @MockBean
    private GiftCertificateDao giftCertificateDao;
    @MockBean
    private TagDao tagDao;
    @MockBean
    private GiftCertificateValidator giftCertificateValidator;
    @Autowired
    GiftCertificateService giftCertificateService;
    GiftCertificateDto giftCertificateDto1;
    GiftCertificate giftCertificate1;
    GiftCertificateDto giftCertificateDto2;
    GiftCertificateDto giftCertificateDto3;
    GiftCertificate giftCertificate2;
    GiftCertificateDto giftCertificateDto4;
    GiftCertificate giftCertificate3;
    GiftCertificateDto giftCertificateDto5;
    GiftCertificateDto giftCertificateDto6;
    GiftCertificate giftCertificate4;

    @BeforeEach
    public void setUp() {
        giftCertificateDto1 = new GiftCertificateDto(0, "New Year", "gift certificate", new BigDecimal("50"), 360,
                ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"),
                null);
        giftCertificate1 = new GiftCertificate(10, "New Year", "gift certificate", new BigDecimal("50"), 360,
                ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"),
                null);
        giftCertificateDto2 = new GiftCertificateDto(10, "New Year", "gift certificate", new BigDecimal("50"), 360,
                ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"),
                null);
        giftCertificateDto3 = new GiftCertificateDto(10, "Happy New Year", null, new BigDecimal("60"), 0, null, null,
                null);
        giftCertificate2 = new GiftCertificate(10, "Happy New Year", "gift certificate", new BigDecimal("60"), 360,
                ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"),
                null);
        giftCertificateDto4 = new GiftCertificateDto(10, "Happy New Year", "gift certificate", new BigDecimal("60"),
                360, ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"
        ), null);
        giftCertificate3 = new GiftCertificate(10, "New Year holiday", "gift", new BigDecimal("100"), 100,
                ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"),
                null);
        giftCertificateDto5 = new GiftCertificateDto(10, "New Year holiday", "gift", new BigDecimal("100"), 100, null
                , null, null);
        giftCertificateDto6 = new GiftCertificateDto(10, "New Year holiday", "gift", new BigDecimal("100"), 100,
                ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"),
                null);
        giftCertificate4 = new GiftCertificate(7, "New Year holiday", "gift certificate", new BigDecimal("80"), 180,
                ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"),
                null);

    }

    @Test
    public void createGiftCertificatePositiveTest() {
        doNothing().when(giftCertificateValidator).validateGiftCertificate(isA(GiftCertificateDto.class));
        when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.empty());
        when(giftCertificateDao.create(isA(GiftCertificate.class))).thenReturn(giftCertificate1);
        GiftCertificateDto actual = giftCertificateService.createGiftCertificate(giftCertificateDto1);
        assertEquals(giftCertificateDto2, actual);
    }

    @Test
    public void createGiftCertificateNegativeTest() {
        doNothing().when(giftCertificateValidator).validateGiftCertificate(isA(GiftCertificateDto.class));
        when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.of(giftCertificate1));
        assertThrows(ResourceAlreadyExistsException.class,
                () -> giftCertificateService.createGiftCertificate(giftCertificateDto1));
    }

    @Test
    public void findGiftCertificateByIdPositiveTest() {
        doNothing().when(giftCertificateValidator).validateId(anyLong());
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate1));
        GiftCertificateDto actual = giftCertificateService.findGiftCertificateById(10);
        assertEquals(giftCertificateDto2, actual);
    }

    @Test
    public void findGiftCertificateByIdNegativeTest() {
        doNothing().when(giftCertificateValidator).validateId(anyLong());
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.findGiftCertificateById(1000));
    }

    @Test
    public void findGiftCertificatesPositiveTest() {
        when(giftCertificateDao.findBySearchParams(isA(Pagination.class), isA(GiftCertificateSearchParams.class))).thenReturn(Arrays.asList(giftCertificate1));
        List<GiftCertificate> actual = giftCertificateDao.findBySearchParams(new Pagination(), new GiftCertificateSearchParams());
        assertEquals(1, actual.size());
    }

    @Test
    public void updateGiftCertificatePositiveTest() {
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate1));
        when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.empty());
        doNothing().when(giftCertificateValidator).validateGiftCertificate(isA(GiftCertificateDto.class));
        when(giftCertificateDao.update(isA(GiftCertificate.class))).thenReturn(giftCertificate2);
        GiftCertificateDto actual = giftCertificateService.updateGiftCertificate(giftCertificateDto3);
        assertEquals(giftCertificateDto4, actual);
    }

    @Test
    public void updateGiftCertificateNegativeTest() {
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> giftCertificateService.updateGiftCertificate(giftCertificateDto1));
    }

    @Test
    public void updateAllGiftCertificatePositiveTest() {
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate2));
        when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.empty());
        doNothing().when(giftCertificateValidator).validateGiftCertificate(isA(GiftCertificateDto.class));
        when(giftCertificateDao.update(isA(GiftCertificate.class))).thenReturn(giftCertificate3);
        GiftCertificateDto actual = giftCertificateService.updateAllGiftCertificate(giftCertificateDto5);
        assertEquals(giftCertificateDto6, actual);
    }

    @Test
    public void updateAllGiftCertificateNegativeTest() {
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(giftCertificate2));
        when(giftCertificateDao.findByName(anyString())).thenReturn(Optional.of(giftCertificate4));
        assertThrows(ResourceAlreadyExistsException.class,
                () -> giftCertificateService.updateAllGiftCertificate(giftCertificateDto5));
    }

    @Test
    public void deleteGiftCertificatePositiveTest() {
        doNothing().when(giftCertificateValidator).validateId(anyLong());
        when(giftCertificateDao.delete(anyLong())).thenReturn(true);
        giftCertificateService.deleteGiftCertificate(10);
        verify(giftCertificateDao, times(1)).delete(anyLong());
    }

    @Test
    public void deleteGiftCertificateNegativeTest() {
        doNothing().when(giftCertificateValidator).validateId(anyLong());
        when(giftCertificateDao.delete(anyLong())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.deleteGiftCertificate(1000));
    }
}