package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDto findGiftCertificateById(long id);

    List<GiftCertificateDto> findGiftCertificates(GiftCertificateSearchParamsDto searchParamsDto);
}
