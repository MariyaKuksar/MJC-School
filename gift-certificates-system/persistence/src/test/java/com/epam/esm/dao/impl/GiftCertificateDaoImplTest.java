package com.epam.esm.dao.impl;

import com.epam.esm.configuration.TestPersistenceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParams;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestPersistenceConfiguration.class)
public class GiftCertificateDaoImplTest {
    private final GiftCertificateDao giftCertificateDao;
    private GiftCertificate giftCertificate1;
    private GiftCertificate giftCertificate2;
    private GiftCertificate giftCertificate3;
    private GiftCertificate giftCertificate4;
    private GiftCertificate giftCertificate5;
    private List<GiftCertificate> giftCertificateList;
    private GiftCertificateSearchParams searchParams1;
    private GiftCertificateSearchParams searchParams2;

    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @BeforeEach
    public void setUp() {
        giftCertificate1 = new GiftCertificate(0, "New Year", "gif certificate", new BigDecimal(100), 180,
                ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"),
                null);
        giftCertificate2 = new GiftCertificate(5L, "New Year", "gif certificate", new BigDecimal(100), 180,
                ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"),
                null);
        giftCertificate3 = new GiftCertificate(3L, "Child", "gift certificate for children", new BigDecimal(30), 50,
                ZonedDateTime.parse("2021-08-04T07:56:47+03:00"), ZonedDateTime.parse("2021-08-04T07:56:47+03:00"),
                Collections.emptyList());
        giftCertificate4 = new GiftCertificate(5L, "Happy New Year", "gif certificate for New Year",
                new BigDecimal(90), 200, ZonedDateTime.parse("2021-08-17T15:20:52+03:00"),
                ZonedDateTime.parse("2021" + "-08-17T15:20:52+03:00"), null);
        giftCertificate5 = new GiftCertificate(5L, "Happy New Year Happy New Year Happy New Year Happy New Year",
                "gif certificate for New Year", new BigDecimal(90), 200,
                ZonedDateTime.parse("2021-08-17T15:20:52+03" + ":00"), ZonedDateTime.parse("2021-08-17T15:20:52+03:00"
        ), null);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "woman"));
        tags.add(new Tag(3L, "relax"));
        GiftCertificate giftCertificate6 = new GiftCertificate(1L, "Woman", "gift certificate for women",
                new BigDecimal(50), 365, ZonedDateTime.parse("2018-08-29T06:12:15+03:00"),
                ZonedDateTime.parse("2018" + "-08-29T06:12:15+03:00"), tags);
        giftCertificateList = new ArrayList<>();
        giftCertificateList.add(giftCertificate6);
        searchParams1 = new GiftCertificateSearchParams("woman", "for women");
        searchParams2 = new GiftCertificateSearchParams("friend", "gift");
    }

    @Test
    public void createPositiveTest() {
        GiftCertificate actual = giftCertificateDao.create(giftCertificate1);
        assertEquals(giftCertificate2, actual);
    }

    @Test
    public void createExceptionTest() {
        assertThrows(DataAccessException.class, () -> giftCertificateDao.create(new GiftCertificate()));
    }

    @Test
    public void findByIdPositiveTest() {
        GiftCertificate actual = giftCertificateDao.findById(3).get();
        assertEquals(giftCertificate3, actual);
    }

    @Test
    public void findByIdNegativeTest() {
        Optional<GiftCertificate> actual = giftCertificateDao.findById(15);
        assertFalse(actual.isPresent());
    }

    @Test
    public void findBySearchParamsPositiveTest() {
        List<GiftCertificate> actual = giftCertificateDao.findBySearchParams(searchParams1);
        assertEquals(giftCertificateList, actual);
    }

    @Test
    public void findBySearchParamsNegativeTest() {
        List<GiftCertificate> actual = giftCertificateDao.findBySearchParams(searchParams2);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void updatePositiveTest() {
        GiftCertificate actual = giftCertificateDao.update(giftCertificate4);
        assertEquals(giftCertificate4, actual);
    }

    @Test
    public void updateExceptionTest() {
        assertThrows(DataAccessException.class, () -> giftCertificateDao.create(giftCertificate5));
    }

    @Test
    public void deletePositiveTest() {
        assertTrue(giftCertificateDao.delete(1));
    }

    @Test
    public void deleteNegativeTest() {
        assertFalse(giftCertificateDao.delete(100));
    }

    @Test
    public void deleteGiftCertificateTagConnectionPositiveTest() {
        assertTrue(giftCertificateDao.deleteGiftCertificateTagConnection(1));
    }
}