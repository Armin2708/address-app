package com.project.adress.street;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StreetRowMapper implements RowMapper<Street> {
    @Override
    public Street mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Street(
                rs.getInt("street_id"),
                rs.getString("name"),
                rs.getInt("city_id")
                );
    }
}
