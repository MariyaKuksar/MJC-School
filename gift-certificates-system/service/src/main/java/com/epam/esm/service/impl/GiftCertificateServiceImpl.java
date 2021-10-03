package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParams;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ErrorDetails;
import com.epam.esm.exception.MessageKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class is implementation of interface {@link GiftCertificateService}
 * for operations with gift certificates.
 *
 * @author Maryia Kuksar
 * @version 1.0
 * @see GiftCertificateService
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final int NUMBER_GIFT_CERTIFICATE_FIELDS = 5;
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final ModelMapper modelMapper;
    private final GiftCertificateValidator giftCertificateValidator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, ModelMapper modelMapper,
                                      GiftCertificateValidator giftCertificateValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
        this.giftCertificateValidator = giftCertificateValidator;
    }

    @Transactional
    @Override
    public GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto) {
        giftCertificateValidator.validateGiftCertificate(giftCertificateDto);
        checkIfNameFree(giftCertificateDto.getName());
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        giftCertificate.setTags(createGiftCertificateTags(giftCertificate.getTags()));
        giftCertificate = giftCertificateDao.create(giftCertificate);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDto findGiftCertificateById(long id) {
        giftCertificateValidator.validateId(id);
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(id);
        return giftCertificateOptional.map(giftCertificate ->
                modelMapper.map(giftCertificateOptional.get(), GiftCertificateDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("invalid id = " + id,
                        new ErrorDetails(MessageKey.RESOURCE_NOT_FOUND_BY_ID, String.valueOf(id), ErrorCode.GIFT_CERTIFICATE_INVALID_ID.getErrorCode())));
    }

    @Override
    public PageDto<GiftCertificateDto> findGiftCertificates(PaginationDto paginationDto, GiftCertificateSearchParamsDto searchParamsDto) {
        Pagination pagination = modelMapper.map(paginationDto, Pagination.class);
        GiftCertificateSearchParams searchParams = modelMapper.map(searchParamsDto, GiftCertificateSearchParams.class);
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findBySearchParams(pagination, searchParams);
        List<GiftCertificateDto> giftCertificateDtoList = giftCertificateList.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
        long totalNumberPositions = giftCertificateDao.getTotalNumber(searchParams);
        return new PageDto<>(giftCertificateDtoList, totalNumberPositions);
    }

    @Transactional
    @Override
    public GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto giftCertificateForUpdateDto = findGiftCertificateById(giftCertificateDto.getId());
        String newName = giftCertificateDto.getName();
        if (newName != null && !newName.equals(giftCertificateForUpdateDto.getName())) {
            checkIfNameFree(newName);
        }
        updateFields(giftCertificateForUpdateDto, giftCertificateDto);
        giftCertificateValidator.validateGiftCertificate(giftCertificateDto);
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        giftCertificate.setTags(createGiftCertificateTags(giftCertificate.getTags()));
        giftCertificate = giftCertificateDao.update(giftCertificate);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    @Transactional
    @Override
    public GiftCertificateDto updateAllGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto giftCertificateForUpdateDto = findGiftCertificateById(giftCertificateDto.getId());
        String newName = giftCertificateDto.getName();
        if (newName != null && !newName.equals(giftCertificateForUpdateDto.getName())) {
            checkIfNameFree(newName);
        }
        giftCertificateDto.setCreateDate(giftCertificateForUpdateDto.getCreateDate());
        giftCertificateValidator.validateGiftCertificate(giftCertificateDto);
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        giftCertificate.setTags(createGiftCertificateTags(giftCertificate.getTags()));
        giftCertificate = giftCertificateDao.update(giftCertificate);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    @Transactional
    @Override
    public void deleteGiftCertificate(long id) {
        giftCertificateValidator.validateId(id);
        if (!giftCertificateDao.delete(id)) {
            throw new ResourceNotFoundException("invalid id = " + id,
                    new ErrorDetails(MessageKey.RESOURCE_NOT_FOUND_BY_ID,
                            String.valueOf(id), ErrorCode.GIFT_CERTIFICATE_INVALID_ID.getErrorCode()));
        }
        giftCertificateDao.deleteConnectionByGiftCertificateId(id);
    }

    private void checkIfNameFree(String name) {
        if (giftCertificateDao.findByName(name).isPresent()) {
            throw new ResourceAlreadyExistsException("such name already exist, invalid name = " + name,
                    new ErrorDetails(MessageKey.NAME_ALREADY_EXIST,
                            name, ErrorCode.GIFT_CERTIFICATE_INVALID_NAME.getErrorCode()));
        }
    }

    private List<Tag> createGiftCertificateTags(List<Tag> tagList) {
        if(tagList != null) {
            tagList = tagList.stream()
                    .map(tag -> tagDao.findByName(tag.getName()).orElseGet(() -> tagDao.create(tag)))
                    .collect(Collectors.toList());
            }
            return tagList;
    }

    private void updateFields(GiftCertificateDto giftCertificateForUpdateDto, GiftCertificateDto giftCertificateDto) {
        int emptyFieldCounter = 0;
        if (giftCertificateDto.getName() == null) {
            giftCertificateDto.setName(giftCertificateForUpdateDto.getName());
            emptyFieldCounter++;
        }
        if (giftCertificateDto.getDescription() == null) {
            giftCertificateDto.setDescription(giftCertificateForUpdateDto.getDescription());
            emptyFieldCounter++;
        }
        if (giftCertificateDto.getPrice() == null) {
            giftCertificateDto.setPrice(giftCertificateForUpdateDto.getPrice());
            emptyFieldCounter++;
        }
        if (giftCertificateDto.getDuration() == 0) {
            giftCertificateDto.setDuration(giftCertificateForUpdateDto.getDuration());
            emptyFieldCounter++;
        }
        if(giftCertificateDto.getTags() == null){
            giftCertificateDto.setTags(giftCertificateForUpdateDto.getTags());
            emptyFieldCounter++;
        }
        if (emptyFieldCounter==NUMBER_GIFT_CERTIFICATE_FIELDS){
            throw new IncorrectParamValueException("no fields to update", Arrays.asList(new ErrorDetails(MessageKey.NO_FIELDS_TO_UPDATE,
                    StringUtils.EMPTY, ErrorCode.NO_FIELDS_TO_UPDATE.getErrorCode())));
        }
        giftCertificateDto.setCreateDate(giftCertificateForUpdateDto.getCreateDate());
        giftCertificateDto.setId(giftCertificateForUpdateDto.getId());
    }
}