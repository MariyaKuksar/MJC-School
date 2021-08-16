package com.epam.esm.dao.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class GiftCertificateWithTagsExtractor implements ResultSetExtractor<List<GiftCertificate>> {

    @Override
    public List<GiftCertificate> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, GiftCertificate> giftCertificateMap = new LinkedHashMap<>();
        while (resultSet.next()) {
            long giftCertificateId = resultSet.getLong(ColumnName.GIFT_CERTIFICATE_ID);
            GiftCertificate giftCertificate = giftCertificateMap.get(giftCertificateId);
            if (giftCertificate == null) {
                giftCertificate = new GiftCertificate();
                giftCertificate.setId(giftCertificateId);
                giftCertificate.setName(resultSet.getString(ColumnName.GIFT_CERTIFICATE_NAME));
                giftCertificate.setDescription(resultSet.getString(ColumnName.DESCRIPTION));
                giftCertificate.setPrice(resultSet.getBigDecimal(ColumnName.PRICE));
                giftCertificate.setDuration(resultSet.getInt(ColumnName.DURATION));
                giftCertificate.setCreateDate(resultSet.getObject(ColumnName.CREATE_DATE, ZonedDateTime.class));
                giftCertificate.setLastUpdateDate(resultSet.getObject(ColumnName.LAST_UPDATE_DATE,
                        ZonedDateTime.class));
                giftCertificateMap.put(giftCertificateId, giftCertificate);
            }
            long tagId = resultSet.getLong(ColumnName.TAG_ID);
            if (tagId != 0) {
                Tag tag = new Tag();
                tag.setId(resultSet.getLong(ColumnName.TAG_ID));
                tag.setName(resultSet.getString(ColumnName.TAG_NAME));
                giftCertificate.addTag(tag);
            }
        }
        return new ArrayList<>(giftCertificateMap.values());
    }
}