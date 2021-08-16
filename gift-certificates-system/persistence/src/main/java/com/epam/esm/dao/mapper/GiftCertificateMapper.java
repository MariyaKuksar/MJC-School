package com.epam.esm.dao.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(resultSet.getLong(ColumnName.GIFT_CERTIFICATE_ID));
        giftCertificate.setName(resultSet.getString(ColumnName.GIFT_CERTIFICATE_NAME));
        giftCertificate.setDescription(resultSet.getString(ColumnName.DESCRIPTION));
        giftCertificate.setPrice(resultSet.getBigDecimal(ColumnName.PRICE));
        giftCertificate.setDuration(resultSet.getInt(ColumnName.DURATION));
        giftCertificate.setCreateDate(resultSet.getObject(ColumnName.CREATE_DATE, ZonedDateTime.class));
        giftCertificate.setLastUpdateDate(resultSet.getObject(ColumnName.LAST_UPDATE_DATE, ZonedDateTime.class));
        return giftCertificate;
    }
}
