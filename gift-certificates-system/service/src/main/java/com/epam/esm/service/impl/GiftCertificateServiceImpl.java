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
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final ModelMapper modelMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, ModelMapper modelMapper) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto) {
        //todo validation if invalid or is not free name throw exception
        if (checkIfNameFree(giftCertificateDto.getName())) {
            GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
            ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.systemDefault());//todo or ZoneOffset.UTC (+00.00
            // created)
            giftCertificate.setCreateDate(currentDate);
            giftCertificate.setLastUpdateDate(currentDate);
            giftCertificate = giftCertificateDao.create(giftCertificate);
            if (giftCertificateDto.getTags() != null) {
                createGiftCertificateTagConnection(giftCertificate);
            }
            giftCertificateDto = modelMapper.map(giftCertificate, GiftCertificateDto.class);
        }
        return giftCertificateDto;
    }

    @Override
    public GiftCertificateDto findGiftCertificateById(long id) {
        //todo validation
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(id);
        return giftCertificateOptional.map(giftCertificate -> modelMapper.map(giftCertificateOptional.get(),
                GiftCertificateDto.class)).orElseThrow(() -> new ResourceNotFoundException("invalid id = " + id,
                MessageKey.GIFT_CERTIFICATE_NOT_FOUND_BY_ID, String.valueOf(id),
                ErrorCode.GIFT_CERTIFICATE.getErrorCode()));
    }

    @Override
    public List<GiftCertificateDto> findGiftCertificates(GiftCertificateSearchParamsDto searchParamsDto) {
        //todo validation
        GiftCertificateSearchParams searchParams = modelMapper.map(searchParamsDto, GiftCertificateSearchParams.class);
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findBySearchParams(searchParams);
        return giftCertificateList.stream().map(giftCertificate -> modelMapper.map(giftCertificate,
                GiftCertificateDto.class)).collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto giftCertificateForUpdateDto = findGiftCertificateById(giftCertificateDto.getId());
        //todo validation if invalid or is not free name throw exception
        String newName = giftCertificateDto.getName();
        if (newName != null && !newName.equals(giftCertificateDto.getName())) {
            checkIfNameFree(newName);
        }
        updateFields(giftCertificateForUpdateDto, giftCertificateDto);
        GiftCertificate updatedGiftCertificate =
                giftCertificateDao.update(modelMapper.map(giftCertificateForUpdateDto, GiftCertificate.class));
        List<TagDto> tagDtoList = giftCertificateDto.getTags();
        if (tagDtoList != null && !tagDtoList.equals(giftCertificateForUpdateDto.getTags())) {
            if (giftCertificateForUpdateDto.getTags() != null) {
                giftCertificateDao.deleteGiftCertificateTagConnection(giftCertificateForUpdateDto.getId());
            }
            updatedGiftCertificate.setTags(tagDtoList.stream().map(tagDto -> modelMapper.map(tagDto, Tag.class)).collect(Collectors.toList()));
            createGiftCertificateTagConnection(updatedGiftCertificate);
        }
        return modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    public void deleteGiftCertificate(long id) {
        //todo validation
        if (!giftCertificateDao.delete(id)) {
            throw new ResourceNotFoundException("invalid id = " + id, MessageKey.GIFT_CERTIFICATE_NOT_FOUND_BY_ID,
                    String.valueOf(id), ErrorCode.GIFT_CERTIFICATE.getErrorCode());
        }
    }

    private boolean checkIfNameFree(String name) {
        return giftCertificateDao.findByName(name).isEmpty();
    }

    private void createGiftCertificateTagConnection(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags().stream().distinct().collect(Collectors.toList());
        tags.forEach(tag -> tag.setId(tagDao.findByName(tag.getName()).orElseGet(() -> tagDao.create(tag)).getId()));
        giftCertificate.setTags(tags);
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
        giftCertificateForUpdateDto.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault())); //todo or
        // ZonedOffset.UTC
    }
}