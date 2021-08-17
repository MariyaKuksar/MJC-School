package com.epam.esm.dao.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.*;

@Component
public class GiftCertificateWithTagsExtractor implements ResultSetExtractor<List<GiftCertificate>> {
    private final TagMapper tagMapper;

    @Autowired
    public GiftCertificateWithTagsExtractor(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public List<GiftCertificate> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<GiftCertificate> giftCertificateList = new ArrayList<>();
        while (resultSet.next()) {
            long giftCertificateId = resultSet.getLong(ColumnName.GIFT_CERTIFICATE_ID);
            Optional<GiftCertificate> giftCertificateOptional =
                    giftCertificateList.stream().filter(giftCertificate -> giftCertificate.getId() == giftCertificateId).findAny();
            GiftCertificate giftCertificate;
            if (giftCertificateOptional.isPresent()) {
                giftCertificate = giftCertificateOptional.get();
            } else {
                giftCertificate = new GiftCertificate();
                giftCertificate.setId(giftCertificateId);
                giftCertificate.setName(resultSet.getString(ColumnName.GIFT_CERTIFICATE_NAME));
                giftCertificate.setDescription(resultSet.getString(ColumnName.DESCRIPTION));
                giftCertificate.setPrice(resultSet.getBigDecimal(ColumnName.PRICE));
                giftCertificate.setDuration(resultSet.getInt(ColumnName.DURATION));
                giftCertificate.setCreateDate(resultSet.getObject(ColumnName.CREATE_DATE, ZonedDateTime.class));
                giftCertificate.setLastUpdateDate(resultSet.getObject(ColumnName.LAST_UPDATE_DATE,
                        ZonedDateTime.class));
                giftCertificateList.add(giftCertificate);
            }
            long tagId = resultSet.getLong(ColumnName.TAG_ID);
            if (tagId != 0) {
                giftCertificate.addTag(tagMapper.mapRow(resultSet, 1));
            }
        }
        return giftCertificateList;
    }
}