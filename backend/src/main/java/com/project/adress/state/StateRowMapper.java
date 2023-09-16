package com.project.adress.state;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StateRowMapper implements RowMapper<State> {
    @Override
    public State mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new State(
                rs.getInt("state_id"),
                rs.getString("name"),
                rs.getString("country_id")
                );
    }
}
