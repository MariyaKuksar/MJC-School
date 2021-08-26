package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class is an endpoint of the API which performs CRUD operations on gift certificates.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Creates new gift certificate, processes POST requests at /gift-certificates
     *
     * @param giftCertificateDto data for creating new gift certificate
     * @return the created gift certificate dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto createGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.createGiftCertificate(giftCertificateDto);
    }

    /**
     * Gets gift certificate by id, processes GET requests at /gift-certificates/{id}
     *
     * @param id the gift certificate id which needs to found
     * @return the founded gift certificate dto
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto getGiftCertificateById(@PathVariable long id) {
        return giftCertificateService.findGiftCertificateById(id);
    }

    /**
     * Gets gift certificates by params, processes GET requests at /gift-certificates
     *
     * @param searchParamsDto data for searching gift certificates
     * @return the founded gift certificate dto
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> getGiftCertificates(GiftCertificateSearchParamsDto searchParamsDto) {
        return giftCertificateService.findGiftCertificates(searchParamsDto);
    }

    /**
     * Updates part of gift certificate data, processes PATCH requests at /gift-certificates/{id}
     *
     * @param id the gift certificate id which needs to update
     * @param giftCertificateDto data for updating gift certificate
     * @return the updated gift certificate dto
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateGiftCertificate(@PathVariable long id,
                                                    @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        return giftCertificateService.updateGiftCertificate(giftCertificateDto);
    }

    /**
     * Updates gift certificate, processes PUT requests at /gift-certificates/{id}
     *
     * @param id the gift certificate id which needs to update
     * @param giftCertificateDto data for updating gift certificate
     * @return the updated gift certificate dto
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateAllGiftCertificate(@PathVariable long id,
                                                       @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        return giftCertificateService.updateAllGiftCertificate(giftCertificateDto);
    }

    /**
     * Deletes gift certificate by id, processes DELETE requests at /gift-certificates/{id}
     *
     * @param id the gift certificate id which needs to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@PathVariable long id) {
        giftCertificateService.deleteGiftCertificate(id);
    }
}

