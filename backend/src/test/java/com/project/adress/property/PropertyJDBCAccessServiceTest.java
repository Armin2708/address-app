package com.project.adress.property;

import com.project.adress.AbstractTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class PropertyJDBCAccessServiceTest extends AbstractTestContainer {

    private PropertyJDBCAccessService underTest;
    private final PropertyRowMapper propertyRowMapper = new PropertyRowMapper();


    @BeforeEach
    void setUp() {
        underTest=new PropertyJDBCAccessService(
                getJdbcTemplate(),
                propertyRowMapper
        );

    }

    @Test
    void selectAllProperties() {
        //Given
        Integer streetId = 1000;
        Property property = new Property(
                111,
                "Tested1",
                streetId
        );
        underTest.insertProperty(property);

        //When
        List<Property> actual = underTest.selectAllProperties(streetId);

        //Then
        assertThat(actual).isNotEmpty();

    }

    @Test
    void selectPropertyById() {
        //Given
        Integer streetId = 1000;
        String Property_number = "Tested2";
        Property property = new Property(
                222,
                Property_number,
                streetId
        );
        underTest.insertProperty(property);

        int id = underTest.selectAllProperties(streetId)
                .stream()
                .filter(c -> c.getProperty_number().equals(Property_number))
                .map(Property::getProperty_id)
                .findFirst()
                .orElseThrow();

        //When
        Optional<Property> actual = underTest.selectPropertyById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c-> {
            assertThat(c.getProperty_id()).isEqualTo(id);
            assertThat(c.getProperty_number()).isEqualTo(property.getProperty_number());
            assertThat(c.getStreet_id()).isEqualTo(property.getStreet_id());
        });

    }

    @Test
    void willReturnEmptyWhenSelectPropertiesById(){
        //Given
        int id = -1;

        //When
        var actual = underTest.selectPropertyById(id);

        //Then
        assertThat(actual).isEmpty();
    }

    @Test
    void insertProperty() {
    }

    @Test
    void existsPropertyWithProperty_number(){

        //Given
        String Property_number = "Tested3";
        Property property = new Property(
                300,
                Property_number,
                1000
        );
        underTest.insertProperty(property);

        //When
        boolean actual = underTest.existPropertyWithNumber(Property_number);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsPropertyWithProperty_numberReturnFalseWhenNotExist(){

        //Given
        String Property_number = "DoesNotExist";

        //When
        boolean actual = underTest.existPropertyWithNumber(Property_number);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existPropertyById() {
        //Given
        Integer streetId = 1000;
        String Property_number = "Tested4";
        Property property = new Property(
                400,
                Property_number,
                streetId
        );
        underTest.insertProperty(property);

        int id = underTest.selectAllProperties(streetId)
                .stream()
                .filter(c -> c.getProperty_number().equals(Property_number))
                .map(Property::getProperty_id)
                .findFirst()
                .orElseThrow();

        //When
        var actual = underTest.existPropertyById(id);

        //Then
        assertThat(actual).isTrue();

    }

    @Test
    void existsPropertyByIdWillReturnFalseWhenIdNotPresent(){

        //Given
        int id = -1;

        //When
        var actual = underTest.existPropertyById(id);

        //Then
        assertThat(actual).isFalse();

    }

    @Test
    void deletePropertyById() {
        //Given
        Integer streetId = 1000;
        String Property_number = "Tested5";
        Property property = new Property(
                500,
                Property_number,
                streetId
        );
        underTest.insertProperty(property);

        int id = underTest.selectAllProperties(streetId)
                .stream()
                .filter(c -> c.getProperty_number().equals(Property_number))
                .map(Property::getProperty_id)
                .findFirst()
                .orElseThrow();

        //When
        underTest.deletePropertyById(id);

        //Then
        Optional<Property> actual = underTest.selectPropertyById(id);
        assertThat(actual).isNotPresent();

    }

    @Test
    void existStreetWithStreetId() {
        //Given
        Integer Street_id = 1000;

        //When
        boolean actual = underTest.existStreetWithStreetId(Street_id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existStreetWithStreetIdReturnFalseWhenNotExist() {
        //Given
        Integer Street_id = -1;

        //When
        boolean actual = underTest.existStreetWithStreetId(Street_id);

        //Then
        assertThat(actual).isFalse();

    }

    @Test
    void updatePropertyProperty_number() {
        //Given
        Integer streetId = 1000;
        String Property_number = "Tested6";
        Property property = new Property(
                600,
                Property_number,
                streetId
        );
        underTest.insertProperty(property);

        int id = underTest.selectAllProperties(streetId)
                .stream()
                .filter(c->c.getProperty_number().equals(Property_number))
                .map(Property::getProperty_id)
                .findFirst()
                .orElseThrow();

        var newProperty_number="Updated1";

        //When
        Property update = new Property();
        update.setProperty_id(id);
        update.setProperty_number(newProperty_number);
        underTest.updateProperty(update);

        //Then
        Optional<Property> actual = underTest.selectPropertyById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c-> {
            assertThat(c.getProperty_id()).isEqualTo(id);
            assertThat(c.getProperty_number()).isEqualTo(newProperty_number);
            assertThat(c.getStreet_id()).isEqualTo(property.getStreet_id());
        });

    }

    @Test
    void updatePropertyStreetId() {
        //Given
        Integer streetId = 1000;
        String Property_number = "Tested7";
        Property property = new Property(
                700,
                Property_number,
                streetId
        );
        underTest.insertProperty(property);

        int id = underTest.selectAllProperties(streetId)
                .stream()
                .filter(c->c.getProperty_number().equals(Property_number))
                .map(Property::getProperty_id)
                .findFirst()
                .orElseThrow();

        var newStreetId=2000;

        //When
        Property update = new Property();
        update.setProperty_id(id);
        update.setStreet_id(newStreetId);
        underTest.updateProperty(update);

        //Then
        Optional<Property> actual = underTest.selectPropertyById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c-> {
            assertThat(c.getProperty_id()).isEqualTo(id);
            assertThat(c.getStreet_id()).isEqualTo(newStreetId);
            assertThat(c.getProperty_number()).isEqualTo(property.getProperty_number());
        });

    }

    @Test
    void updateAllPropertyProperties() {
        //Given
        Integer streetId = 1000;
        String Property_number = "Tested8";
        Property property=new Property(
                800,
                Property_number,
                streetId
        );
        underTest.insertProperty(property);

        int id = underTest.selectAllProperties(streetId)
                .stream()
                .filter(c -> c.getProperty_number().equals(Property_number))
                .map(Property::getProperty_id)
                .findFirst()
                .orElseThrow();

        String newProperty_number="Updated2";

        //When
        Property update= new Property();
        update.setProperty_id(id);
        update.setProperty_number(newProperty_number);
        update.setStreet_id(2000);

        underTest.updateProperty(update);

        //Then
        Optional<Property> actual = underTest.selectPropertyById(id);

        assertThat(actual).isPresent().hasValueSatisfying(updated -> {
            assertThat(updated.getProperty_id()).isEqualTo(id);
            assertThat(updated.getProperty_number()).isEqualTo(newProperty_number);
            assertThat(updated.getStreet_id()).isEqualTo(2000);
        });

    }

    @Test
    void willNotUpdateWhenNoUpdate() {
        //Given
        Integer streetId = 1000;
        String Property_number = "Tested9";
        Property property=new Property(
                900,
                Property_number,
                streetId
        );
        underTest.insertProperty(property);

        int id = underTest.selectAllProperties(streetId)
                .stream()
                .filter(c -> c.getProperty_number().equals(Property_number))
                .map(Property::getProperty_id)
                .findFirst()
                .orElseThrow();

        //When
        Property update= new Property();

        underTest.updateProperty(update);

        //Then
        Optional<Property> actual = underTest.selectPropertyById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getProperty_id()).isEqualTo(id);
            assertThat(c.getProperty_number()).isEqualTo(property.getProperty_number());

            assertThat(c.getStreet_id()).isEqualTo(property.getStreet_id());
        });

    }
}