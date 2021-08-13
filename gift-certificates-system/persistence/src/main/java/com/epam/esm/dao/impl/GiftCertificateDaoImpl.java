package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String SQL_INSERT_GIFT_CERTIFICATE = "INSERT INTO GIFT_CERTIFICATE (NAME, DESCRIPTION, " +
            "PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE) VALUES (?,?,?,?,?,?)";
    private static final String SQL_SELECT_ALL_GIFT_CERTIFICATE =
            "SELECT ID, NAME, DESCRIPTION, PRICE, DURATION, " + "CREATE_DATE, LAST_UPDATE_DATE FROM GIFT_CERTIFICATE";
    private static final String SQL_SELECT_GIFT_CERTIFICATE_BY_ID =
            "SELECT ID, NAME, DESCRIPTION, PRICE, DURATION, " + "CREATE_DATE, LAST_UPDATE_DATE FROM GIFT_CERTIFICATE " +
                    "WHERE ID=?";

    private JdbcTemplate jdbcTemplate;

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
        return jdbcTemplate.query(SQL_SELECT_ALL_GIFT_CERTIFICATE, new BeanPropertyRowMapper(GiftCertificate.class));
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {

        return jdbcTemplate.queryForStream(SQL_SELECT_GIFT_CERTIFICATE_BY_ID,
                new BeanPropertyRowMapper(GiftCertificate.class), id).findFirst();
    }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
