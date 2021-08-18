package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateWithTagsExtractor;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String SQL_INSERT_GIFT_CERTIFICATE = "INSERT INTO GIFT_CERTIFICATE (NAME, DESCRIPTION, " +
            "PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE) VALUES (?,?,?,?,?,?)";
    private static final String SQL_JOIN_GIF_CERTIFICATE_TAG_CONNECTION_JOIN_TAG = "LEFT JOIN " +
            "GIFT_CERTIFICATE_TAG_CONNECTION ON GIFT_CERTIFICATE.ID=GIFT_CERTIFICATE_ID LEFT JOIN TAG ON TAG_ID=TAG.ID";
    private static final String SQL_SELECT_ALL_GIFT_CERTIFICATES = "SELECT GIFT_CERTIFICATE.ID, GIFT_CERTIFICATE" +
            ".NAME," + " DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE, TAG.ID, TAG.NAME FROM " +
            "GIFT_CERTIFICATE " + SQL_JOIN_GIF_CERTIFICATE_TAG_CONNECTION_JOIN_TAG;
    private static final String SQL_SELECT_GIFT_CERTIFICATE_BY_ID = "SELECT GIFT_CERTIFICATE.ID, GIFT_CERTIFICATE" +
            ".NAME, DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE, TAG.ID, TAG.NAME FROM " +
            "GIFT_CERTIFICATE " + SQL_JOIN_GIF_CERTIFICATE_TAG_CONNECTION_JOIN_TAG + " WHERE GIFT_CERTIFICATE.ID=?";
    private static final String SQL_UPDATE_GIFT_CERTIFICATE =
            "UPDATE GIFT_CERTIFICATE SET NAME=?, DESCRIPTION=?, " + "PRICE=?, DURATION=?, CREATE_DATE=?, " +
                    "LAST_UPDATE_DATE=? WHERE ID=?";
    private static final String SQL_DELETE_GIFT_CERTIFICATE = "DELETE FROM GIFT_CERTIFICATE WHERE ID=?";
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateWithTagsExtractor giftCertificateWithTagsExtractor;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate,
                                  GiftCertificateWithTagsExtractor giftCertificateWithTagsExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateWithTagsExtractor = giftCertificateWithTagsExtractor;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_GIFT_CERTIFICATE,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, giftCertificate.getName());
            statement.setString(2, giftCertificate.getDescription());
            statement.setBigDecimal(3, giftCertificate.getPrice());
            statement.setInt(4, giftCertificate.getDuration());
            statement.setObject(5, giftCertificate.getCreateDate());
            statement.setObject(6, giftCertificate.getLastUpdateDate());
            return statement;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            giftCertificate.setId(keyHolder.getKey().longValue());
        }
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_GIFT_CERTIFICATES, giftCertificateWithTagsExtractor);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return jdbcTemplate.query(SQL_SELECT_GIFT_CERTIFICATE_BY_ID, giftCertificateWithTagsExtractor, id).stream().findFirst();
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(SQL_UPDATE_GIFT_CERTIFICATE, giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(), giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(), giftCertificate.getId());
        return giftCertificate;
    }

    @Override
    public boolean delete(long id) {
        return (jdbcTemplate.update(SQL_DELETE_GIFT_CERTIFICATE, id) > 0);
    }
}
