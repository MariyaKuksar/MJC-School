package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    public static final String SQL_INSERT_TAG = "INSERT INTO TAG (NAME) VALUES (?)";
    public static final String SQL_SELECT_ALL_TAGS = "SELECT ID, NAME FROM TAG";
    public static final String SQL_SELECT_TAG_BY_ID = "SELECT ID, NAME FROM TAG WHERE ID=?";
    public static final String SQL_DELETE_TAG = "DELETE FROM TAG WHERE ID=?";
    private JdbcTemplate jdbcTemplate;
    private TagMapper tagMapper;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TAG,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, tag.getName());
            return statement;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            tag.setId(keyHolder.getKey().longValue());
        }
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_TAGS, tagMapper);
    }

    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.queryForStream(SQL_SELECT_TAG_BY_ID, tagMapper, id).findFirst();
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(SQL_DELETE_TAG, id);
    }
}