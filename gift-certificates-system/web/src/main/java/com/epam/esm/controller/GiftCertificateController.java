package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping(path = "/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable long id) {
        return giftCertificateService.findGiftCertificateById(id);
    }

    @GetMapping
    public List<GiftCertificateDto> getGiftCertificates(GiftCertificateSearchParamsDto searchParamsDto) {
        return giftCertificateService.findGiftCertificates(searchParamsDto);
    }


}
