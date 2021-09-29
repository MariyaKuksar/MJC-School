package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;

import java.util.List;

/**
 * The interface for operations with gift certificates.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
public interface GiftCertificateService {

    /**
     * Creates new gift certificate in database,
     * if gift certificate has new tags they will be created
     *
     * @param giftCertificateDto data for creating new gift certificate
     * @return the created gift certificate dto
     */
    GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto);

    /**
     * Finds gift certificate by id
     *
     * @param id the gift certificate id which needs to found
     * @return the founded gift certificate dto
     */
    GiftCertificateDto findGiftCertificateById(long id);

    /**
     * Finds gift certificates by params
     *
     * @param searchParamsDto data for searching gift certificates
     * @return the list of founded gift certificate dto
     */
    PageDto<GiftCertificateDto> findGiftCertificates(PaginationDto paginationDto, GiftCertificateSearchParamsDto searchParamsDto);

    /**
     * Updates part of gift certificate data,
     * if field value is null it will be not updated,
     * if gift certificate has new tags they will be added
     *
     * @param giftCertificateDto data for updating gift certificate
     * @return the updated gift certificate dto
     */
    GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto);

    /**
     * Updates gift certificate, all fields must be filled,
     * if gift certificate has new tags they will be added
     *
     * @param giftCertificateDto data for updating gift certificate
     * @return the updated gift certificate dto
     */
    GiftCertificateDto updateAllGiftCertificate (GiftCertificateDto giftCertificateDto);

    /**
     * Deletes gift certificate by id
     *
     * @param id the gift certificate id which needs to delete
     */
    void deleteGiftCertificate(long id);
}
