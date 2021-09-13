package com.epam.esm.dao.mapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class used by JdbcTemplate for mapping rows of a ResultSet on tag.
 * @author Maryia Kuksar
 * @version 1.0
 * @see RowMapper
 */
@Component
public class TagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        Tag tag = new Tag();
        tag.setId(resultSet.getLong(ColumnName.TAG_ID));
        tag.setName(resultSet.getString(ColumnName.TAG_NAME));
        return tag;
    }
}
