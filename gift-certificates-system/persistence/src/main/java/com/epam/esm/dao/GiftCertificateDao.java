package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {

    GiftCertificate create(GiftCertificate giftCertificate);

    List<GiftCertificate> findAll();

    Optional<GiftCertificate> findById(long id);

    GiftCertificate update(GiftCertificate giftCertificate);

    boolean delete(long id);
}
