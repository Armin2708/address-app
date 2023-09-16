package com.project.adress.city;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CityRowMapper implements RowMapper<City> {
    @Override
    public City mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new City(
                rs.getInt("city_id"),
                rs.getString("name"),
                rs.getInt("state_id")
                );
    }
}
