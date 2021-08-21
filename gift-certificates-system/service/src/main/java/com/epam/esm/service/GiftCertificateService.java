package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto findGiftCertificateById(long id);

    List<GiftCertificateDto> findGiftCertificates(GiftCertificateSearchParamsDto searchParamsDto);

    GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto);

    void deleteGiftCertificate(long id);
}
