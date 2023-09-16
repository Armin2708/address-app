package com.project.adress.street;

import com.project.adress.city.City;
import com.project.adress.city.CityRowMapper;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StreetRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        //Given
        StreetRowMapper streetRowMapper = new StreetRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("street_id")).thenReturn(1000);
        when(resultSet.getString("name")).thenReturn("Test1");
        when(resultSet.getInt("city_id")).thenReturn(100);

        //When
        Street actual = streetRowMapper.mapRow(resultSet,1);

        //Then
        Street expected = new Street(1000,"Test1",100);
        assertThat(actual).isEqualTo(expected);

    }
}