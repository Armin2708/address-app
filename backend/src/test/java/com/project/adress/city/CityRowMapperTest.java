package com.project.adress.city;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CityRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        //Given
        CityRowMapper cityRowMapper = new CityRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("city_id")).thenReturn(100);
        when(resultSet.getString("name")).thenReturn("Test1");
        when(resultSet.getInt("state_id")).thenReturn(10);

        //When
        City actual = cityRowMapper.mapRow(resultSet,1);

        //Then
        City expected = new City(100,"Test1",10);
        assertThat(actual).isEqualTo(expected);

    }
}