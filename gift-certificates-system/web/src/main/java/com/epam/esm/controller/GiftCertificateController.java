package com.epam.esm.controller;
import static  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static  org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.converter.ParamsToDtoConverter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Map;

/**
 * Class is an endpoint of the API which performs CRUD operations on gift certificates.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {
    private static final String DELETE = "delete";
    private static final String UPDATE = "update";
    private final GiftCertificateService giftCertificateService;
    private final ParamsToDtoConverter paramsToDtoConverter;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, ParamsToDtoConverter paramsToDtoConverter) {
        this.giftCertificateService = giftCertificateService;
        this.paramsToDtoConverter = paramsToDtoConverter;
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
        GiftCertificateDto createdGiftCertificateDto = giftCertificateService.createGiftCertificate(giftCertificateDto);
        addLinks(giftCertificateDto);
        return createdGiftCertificateDto;
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
        GiftCertificateDto giftCertificateDto = giftCertificateService.findGiftCertificateById(id);
        addLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    /**
     * Gets gift certificates by params, processes GET requests at /gift-certificates
     *
     * @param params data for searching gift certificates
     * @return the founded gift certificate dto
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDto<GiftCertificateDto> getGiftCertificates(@RequestParam Map<String, String> params) {
        PaginationDto paginationDto = paramsToDtoConverter.getPaginationDto(params);
        GiftCertificateSearchParamsDto searchParamsDto = paramsToDtoConverter.getGiftCertificatesSearchParamsDto(params);
        PageDto<GiftCertificateDto> pageDto = giftCertificateService.findGiftCertificates(paginationDto, searchParamsDto);
        pageDto.getPagePositions().forEach(this::addLinks);
        return pageDto;
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
        GiftCertificateDto updatedGiftCertificateDto = giftCertificateService.updateGiftCertificate(giftCertificateDto);
        addLinks(updatedGiftCertificateDto);
        return updatedGiftCertificateDto;
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
        GiftCertificateDto updatedGiftCertificateDto = giftCertificateService.updateAllGiftCertificate(giftCertificateDto);
        addLinks(updatedGiftCertificateDto);
        return updatedGiftCertificateDto;
    }

    /**
     * Deletes gift certificate by id, processes DELETE requests at /gift-certificates/{id}
     *
     * @param id the gift certificate id which needs to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteGiftCertificate(@PathVariable long id) {
        giftCertificateService.deleteGiftCertificate(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private void addLinks (GiftCertificateDto giftCertificateDto){
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificateById(giftCertificateDto.getId())).withSelfRel());
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).updateGiftCertificate(giftCertificateDto.getId(), giftCertificateDto)).withRel(UPDATE));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).deleteGiftCertificate(giftCertificateDto.getId())).withRel(DELETE));
        giftCertificateDto.getTags().forEach(tagDto ->
                tagDto.add(linkTo(methodOn(TagController.class).getTagById(tagDto.getId())).withSelfRel()));
    }
}

