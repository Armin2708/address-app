package com.project.adress.state;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StateRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        //Given
        StateRowMapper stateRowMapper = new StateRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("state_id")).thenReturn(10);
        when(resultSet.getString("name")).thenReturn("California");
        when(resultSet.getString("country_id")).thenReturn("FRA");

        //When
        State actual = stateRowMapper.mapRow(resultSet,1);

        //Then
        State expected = new State(10,"California","FRA");
        assertThat(actual).isEqualTo(expected);

    }
}