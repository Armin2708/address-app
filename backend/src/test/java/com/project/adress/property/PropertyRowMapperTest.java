package com.project.adress.property;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PropertyRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        //Given
        PropertyRowMapper propertyRowMapper = new PropertyRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("property_id")).thenReturn(10000);
        when(resultSet.getString("property_number")).thenReturn("Test1");
        when(resultSet.getInt("street_id")).thenReturn(1000);

        //When
        Property actual = propertyRowMapper.mapRow(resultSet,1);

        //Then
        Property expected = new Property(10000,"Test1",1000);
        assertThat(actual).isEqualTo(expected);

    }
}