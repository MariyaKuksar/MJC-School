package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParams;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {

    GiftCertificate create(GiftCertificate giftCertificate);

    void createGiftCertificateTagConnection (GiftCertificate giftCertificate);

    Optional<GiftCertificate> findById(long id);

    Optional<GiftCertificate> findByName(String name);

    List<GiftCertificate> findBySearchParams(GiftCertificateSearchParams searchParams);

    GiftCertificate update(GiftCertificate giftCertificate);

    boolean delete(long id);
}
