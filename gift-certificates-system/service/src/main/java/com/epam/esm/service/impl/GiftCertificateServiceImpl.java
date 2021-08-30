package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParams;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.MessageKey;
import com.epam.esm.exception.ResourceAlreadyExistsException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.systemDefault());
        giftCertificateDto.setCreateDate(currentDate);
        giftCertificateDto.setLastUpdateDate(currentDate);
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        giftCertificate = giftCertificateDao.create(giftCertificate);
        if (giftCertificateDto.getTags() != null) {
            giftCertificateDto.setId(giftCertificate.getId());
            createGiftCertificateTagConnection(giftCertificateDto);
            giftCertificate.setTags(giftCertificateDto.getTags().stream().map(tagDto -> modelMapper.map(tagDto,
                    Tag.class)).collect(Collectors.toList()));
        }
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDto findGiftCertificateById(long id) {
        giftCertificateValidator.validateId(id);
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(id);
        return giftCertificateOptional.map(giftCertificate -> modelMapper.map(giftCertificateOptional.get(),
                GiftCertificateDto.class)).orElseThrow(() -> new ResourceNotFoundException("invalid id = " + id,
                MessageKey.RESOURCE_NOT_FOUND_BY_ID, String.valueOf(id), ErrorCode.GIFT_CERTIFICATE.getErrorCode()));
    }

    @Override
    public List<GiftCertificateDto> findGiftCertificates(GiftCertificateSearchParamsDto searchParamsDto) {
        GiftCertificateSearchParams searchParams = modelMapper.map(searchParamsDto, GiftCertificateSearchParams.class);
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findBySearchParams(searchParams);
        return giftCertificateList.stream().map(giftCertificate -> modelMapper.map(giftCertificate,
                GiftCertificateDto.class)).collect(Collectors.toList());
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
        giftCertificateValidator.validateGiftCertificate(giftCertificateForUpdateDto);
        updateGiftCertificateTagConnection(giftCertificateForUpdateDto, giftCertificateDto);
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateForUpdateDto, GiftCertificate.class);
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
        giftCertificateDto.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        giftCertificateValidator.validateGiftCertificate(giftCertificateDto);
        if (giftCertificateForUpdateDto.getTags() != null) {
            giftCertificateDao.deleteGiftCertificateTagConnection(giftCertificateForUpdateDto.getId());
        }
        if (giftCertificateDto.getTags() != null) {
            createGiftCertificateTagConnection(giftCertificateDto);
        }
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        giftCertificate = giftCertificateDao.update(giftCertificate);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    @Override
    public void deleteGiftCertificate(long id) {
        giftCertificateValidator.validateId(id);
        if (!giftCertificateDao.delete(id)) {
            throw new ResourceNotFoundException("invalid id = " + id, MessageKey.RESOURCE_NOT_FOUND_BY_ID,
                    String.valueOf(id), ErrorCode.GIFT_CERTIFICATE.getErrorCode());
        }
    }

    private void checkIfNameFree(String name) {
        if (giftCertificateDao.findByName(name).isPresent()) {
            throw new ResourceAlreadyExistsException("such name already exist, invalid name = " + name,
                    MessageKey.NAME_ALREADY_EXIST, name, ErrorCode.GIFT_CERTIFICATE.getErrorCode());
        }
    }

    private void createGiftCertificateTagConnection(GiftCertificateDto giftCertificateDto) {
        List<Tag> tags = giftCertificateDto.getTags().stream().distinct().map(tagDto -> modelMapper.map(tagDto,
                Tag.class)).collect(Collectors.toList());
        tags.forEach(tag -> tag.setId(tagDao.findByName(tag.getName()).orElseGet(() -> tagDao.create(tag)).getId()));
        giftCertificateDto.setTags(tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList()));
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        giftCertificateDao.createGiftCertificateTagConnection(giftCertificate);
    }

    private void updateFields(GiftCertificateDto giftCertificateForUpdateDto, GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getName() != null) {
            giftCertificateForUpdateDto.setName(giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null) {
            giftCertificateForUpdateDto.setDescription(giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null) {
            giftCertificateForUpdateDto.setPrice(giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getDuration() != 0) {
            giftCertificateForUpdateDto.setDuration(giftCertificateDto.getDuration());
        }
        giftCertificateForUpdateDto.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));        //
    }

    private void updateGiftCertificateTagConnection(GiftCertificateDto giftCertificateForUpdateDto,
                                                    GiftCertificateDto giftCertificateDto) {
        List<TagDto> tagDtoList = giftCertificateDto.getTags();
        if (tagDtoList != null && !tagDtoList.equals(giftCertificateForUpdateDto.getTags())) {
            tagDtoList.forEach(tagDto -> giftCertificateValidator.validateTagName(tagDto.getName()));
            if (giftCertificateForUpdateDto.getTags() != null) {
                giftCertificateDao.deleteGiftCertificateTagConnection(giftCertificateForUpdateDto.getId());
            }
            giftCertificateForUpdateDto.setTags(tagDtoList);
            createGiftCertificateTagConnection(giftCertificateForUpdateDto);
        }
    }
}