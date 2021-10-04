package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParams;
import com.epam.esm.entity.Pagination;

import java.util.List;
import java.util.Optional;

/**
 * The interface for working with gift_certificate and
 * gift_certificate_tag_connection tables in database.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
public interface GiftCertificateDao {

    /**
     * Creates new gift certificate in database
     *
     * @param giftCertificate the gift certificate for creating
     * @return the created gift certificate
     */
    GiftCertificate create(GiftCertificate giftCertificate);

    /**
     * Finds gift certificate in database by id
     *
     * @param id the gift certificate id which needs to find
     * @return the found gift certificate
     */
    Optional<GiftCertificate> findById(long id);

    /**
     * Finds gift certificate in database by name
     *
     * @param name the gift certificate name which needs to find
     * @return the found gift certificate
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * Finds gift certificate in database by search params
     *
     * @param pagination   the data for pagination
     * @param searchParams data for searching gift certificates
     * @return the list of found gift certificate
     */
    List<GiftCertificate> findBySearchParams(Pagination pagination, GiftCertificateSearchParams searchParams);

    /**
     * Updates gift certificate in database
     *
     * @param giftCertificate the gift certificate for updating
     * @return the updated gift certificates
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Deletes gift certificate in database
     *
     * @param id the gift certificate id which needs to delete
     * @return true if gift certificate was deleted, else false
     */
    boolean delete(long id);

    /**
     * Deletes gift certificate and tag connections in database
     *
     * @param id the gift certificate id
     */
    void deleteConnectionByGiftCertificateId(long id);

    /**
     * Gets the total number of gift certificates by search params
     *
     * @param searchParams data for searching gift certificates
     * @return the total number of gift certificates by search params
     */
    long getTotalNumber(GiftCertificateSearchParams searchParams);
}
