package com.project.adress.property;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PropertyRowMapper implements RowMapper<Property> {
    @Override
    public Property mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Property(
                rs.getInt("property_id"),
                rs.getString("name"),
                rs.getInt("street_id")
                );
    }
}
