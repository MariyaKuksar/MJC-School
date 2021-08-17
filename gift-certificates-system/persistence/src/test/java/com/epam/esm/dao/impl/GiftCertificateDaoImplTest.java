package com.epam.esm.dao.impl;

import com.epam.esm.configuration.TestPersistenceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.BeforeEach;
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
public class GiftCertificateDaoImplTest {
    private final GiftCertificateDao giftCertificateDao;
    private GiftCertificate giftCertificate1;
    private GiftCertificate giftCertificate2;
    private GiftCertificate giftCertificate3;
    private GiftCertificate giftCertificate4;
    private GiftCertificate giftCertificate5;

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
                null);
        giftCertificate4 = new GiftCertificate(5L, "Happy New Year", "gif certificate for New Year",
                new BigDecimal(90), 200, ZonedDateTime.parse("2021-08-17T15:20:52+03:00"),
                ZonedDateTime.parse("2021" + "-08-17T15:20:52+03:00"), null);
        giftCertificate5 = new GiftCertificate(5L, "Happy New Year Happy New Year Happy New Year Happy New Year",
                "gif certificate for New Year", new BigDecimal(90), 200,
                ZonedDateTime.parse("2021-08-17T15:20:52+03" + ":00"),
                ZonedDateTime.parse("2021" + "-08-17T15:20:52" + "+03:00"), null);
    }

    @Test
    public void createPositiveTest() {
        GiftCertificate actual = giftCertificateDao.create(giftCertificate1);
        assertEquals(giftCertificate2, actual);
    }

    @Test
    public void createExceptionTest() {
        assertThrows(DataIntegrityViolationException.class, () -> giftCertificateDao.create(new GiftCertificate()));
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
    public void updatePositiveTest() {
        GiftCertificate actual = giftCertificateDao.update(giftCertificate4);
        assertEquals(giftCertificate4, actual);
    }

    @Test
    public void updateExceptionTest() {
        assertThrows(DataIntegrityViolationException.class, () -> giftCertificateDao.create(giftCertificate5));
    }
}