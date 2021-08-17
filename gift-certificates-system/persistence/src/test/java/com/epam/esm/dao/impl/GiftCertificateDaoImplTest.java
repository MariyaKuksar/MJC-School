package com.epam.esm.dao.impl;

import com.epam.esm.configuration.TestPersistenceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPersistenceConfiguration.class)
class GiftCertificateDaoImplTest {
    private final GiftCertificateDao giftCertificateDao;
    private static GiftCertificate giftCertificate1;
    private static GiftCertificate giftCertificate2;
    private static GiftCertificate giftCertificate3;
    private static GiftCertificate giftCertificate4;
    private static GiftCertificate giftCertificate5;

    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @BeforeAll
    static void setUp() {
        giftCertificate1 = new GiftCertificate(0, "New Year", "gif certificate", new BigDecimal(100), 180,
                ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"),
                null);
        giftCertificate2 = new GiftCertificate(5L, "New Year", "gif certificate", new BigDecimal(100), 180,
                ZonedDateTime.parse("2021-08-17T14:11:52+03:00"), ZonedDateTime.parse("2021-08-17T14:11:52+03:00"),
                null);
        giftCertificate3 = new GiftCertificate(3L, "Child", "gift certificate for children", new BigDecimal(30), 50,
                ZonedDateTime.parse("2021-08-04T07:56:47+03:00"), ZonedDateTime.parse("2021-08-04T07:56:47+03:00"),
                null);
        giftCertificate4 = new GiftCertificate(5L, "Happy New Year", "gif certificate for New Year",
                new BigDecimal(90), 200, ZonedDateTime.parse("2021-08-17T15:20:52+03:00"),
                ZonedDateTime.parse("2021" + "-08-17T15:20:52+03:00"), null);
        giftCertificate5 = new GiftCertificate(5L, "Happy New Year Happy New Year Happy New Year Happy New Year",
                "gif certificate for New Year", new BigDecimal(90), 200, ZonedDateTime.parse("2021-08-17T15:20:52+03" +
                ":00"), ZonedDateTime.parse("2021" + "-08-17T15:20:52+03:00"), null);
    }

    @AfterAll
    static void tearDown() {
        giftCertificate1 = null;
        giftCertificate2 = null;
        giftCertificate3 = null;
        giftCertificate4 = null;
        giftCertificate5 = null;

    }

    @Test
    void createPositiveTest() {
        GiftCertificate actual = giftCertificateDao.create(giftCertificate1);
        assertEquals(giftCertificate2, actual);
    }

    @Test
    void createExceptionTest() {
        assertThrows(DataIntegrityViolationException.class, () -> giftCertificateDao.create(new GiftCertificate()));
    }

    @Test
    void findByIdPositiveTest() {
        GiftCertificate actual = giftCertificateDao.findById(3).get();
        assertEquals(giftCertificate3, actual);
    }

    @Test
    void findByIdNegativeTest() {
        Optional<GiftCertificate> actual = giftCertificateDao.findById(15);
        assertFalse(actual.isPresent());
    }

    @Test
    void updatePositiveTest() {
        GiftCertificate actual = giftCertificateDao.update(giftCertificate4);
        assertEquals(giftCertificate4, actual);
    }

    @Test
    void updateExceptionTest() {
        assertThrows(DataIntegrityViolationException.class, () -> giftCertificateDao.create(giftCertificate5));
    }
}