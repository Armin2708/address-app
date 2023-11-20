package com.project.adress.street;

import com.project.adress.AbstractTestContainer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class StreetJDBCAccessServiceTest extends AbstractTestContainer {

    private StreetJDBCAccessService underTest;
    private final StreetRowMapper StreetRowMapper = new StreetRowMapper();


    @BeforeEach
    void setUp() {
        underTest=new StreetJDBCAccessService(
                getJdbcTemplate(),
                StreetRowMapper
        );

    }

    @Test
    void selectAllStreets() {
        //Given
        Integer cityId=100;
        Street street = new Street(
                1111,
                "Tested1",
                cityId
        );
        underTest.insertStreet(street);

        //When
        List<Street> actual = underTest.selectAllStreets(cityId);

        //Then
        assertThat(actual).isNotEmpty();

    }

    @Test
    void selectStreetById() {
        //Given
        Integer cityId=100;
        String name = "Tested2";
        Street street = new Street(
                2222,
                name,
                cityId
        );
        underTest.insertStreet(street);

        int id = underTest.selectAllStreets(cityId)
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(Street::getStreet_id)
                .findFirst()
                .orElseThrow();

        //When
        Optional<Street> actual = underTest.selectStreetById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c-> {
            assertThat(c.getStreet_id()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(street.getName());
            assertThat(c.getCity_id()).isEqualTo(street.getCity_id());
        });

    }

    @Test
    void willReturnEmptyWhenSelectStreetsById(){
        //Given
        int id = -1;

        //When
        var actual = underTest.selectStreetById(id);

        //Then
        assertThat(actual).isEmpty();
    }

    @Test
    void insertStreet() {
    }

    @Test
    void existsStreetWithName(){

        //Given
        Integer cityId=100;
        String name = "Tested3";
        Street street = new Street(
                3000,
                name,
                cityId
        );
        underTest.insertStreet(street);

        //When
        boolean actual = underTest.existStreetWithName(name);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsStreetWithNameReturnFalseWhenNotExist(){

        //Given
        String name = "DoesNotExist";

        //When
        boolean actual = underTest.existStreetWithName(name);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existStreetById() {
        //Given
        Integer cityId=100;
        String name = "Tested4";
        Street street = new Street(
                4000,
                name,
                cityId
        );
        underTest.insertStreet(street);

        int id = underTest.selectAllStreets(cityId)
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(Street::getStreet_id)
                .findFirst()
                .orElseThrow();

        //When
        var actual = underTest.existStreetById(id);

        //Then
        assertThat(actual).isTrue();

    }

    @Test
    void existsStreetByIdWillReturnFalseWhenIdNotPresent(){

        //Given
        int id = -1;

        //When
        var actual = underTest.existStreetById(id);

        //Then
        assertThat(actual).isFalse();

    }

    @Test
    void deleteStreetById() {
        //Given
        Integer cityId=100;
        String name = "Tested5";
        Street street = new Street(
                5000,
                name,
                cityId
        );
        underTest.insertStreet(street);

        int id = underTest.selectAllStreets(cityId)
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(Street::getStreet_id)
                .findFirst()
                .orElseThrow();

        //When
        underTest.deleteStreetById(id);

        //Then
        Optional<Street> actual = underTest.selectStreetById(id);
        assertThat(actual).isNotPresent();

    }

    @Test
    void existCityWithCityId() {
        //Given
        Integer City_id = 100;

        //When
        boolean actual = underTest.existCityWithCityId(City_id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existCityWithCityIdReturnFalseWhenNotExist() {
        //Given
        Integer City_id = -1;

        //When
        boolean actual = underTest.existCityWithCityId(City_id);

        //Then
        assertThat(actual).isFalse();

    }

    @Test
    void updateStreetName() {
        //Given
        Integer cityId=100;
        String name = "Tested6";
        Street street = new Street(
                6000,
                name,
                cityId
        );
        underTest.insertStreet(street);

        int id = underTest.selectAllStreets(cityId)
                .stream()
                .filter(c->c.getName().equals(name))
                .map(Street::getStreet_id)
                .findFirst()
                .orElseThrow();

        var newName="Updated1";

        //When
        Street update = new Street();
        update.setStreet_id(id);
        update.setName(newName);
        underTest.updateStreet(update);

        //Then
        Optional<Street> actual = underTest.selectStreetById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c-> {
            assertThat(c.getStreet_id()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName);
            assertThat(c.getCity_id()).isEqualTo(street.getCity_id());
        });

    }

    @Test
    void updateStreetCityId() {
        //Given
        Integer cityId=100;
        String name = "Tested7";
        Street street = new Street(
                7000,
                name,
                cityId
        );
        underTest.insertStreet(street);

        int id = underTest.selectAllStreets(cityId)
                .stream()
                .filter(c->c.getName().equals(name))
                .map(Street::getStreet_id)
                .findFirst()
                .orElseThrow();

        var newCityId=200;

        //When
        Street update = new Street();
        update.setStreet_id(id);
        update.setCity_id(newCityId);
        underTest.updateStreet(update);

        //Then
        Optional<Street> actual = underTest.selectStreetById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c-> {
            assertThat(c.getStreet_id()).isEqualTo(id);
            assertThat(c.getCity_id()).isEqualTo(newCityId);
            assertThat(c.getName()).isEqualTo(street.getName());
        });

    }

    @Test
    void updateAllStreetProperties() {
        //Given
        Integer cityId=100;
        String name = "Tested8";
        Street street=new Street(
                8000,
                name,
                cityId
        );
        underTest.insertStreet(street);

        int id = underTest.selectAllStreets(cityId)
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(Street::getStreet_id)
                .findFirst()
                .orElseThrow();

        String newName="Updated2";

        //When
        Street update= new Street();
        update.setStreet_id(id);
        update.setName(newName);
        update.setCity_id(200);

        underTest.updateStreet(update);

        //Then
        Optional<Street> actual = underTest.selectStreetById(id);

        assertThat(actual).isPresent().hasValueSatisfying(updated -> {
            assertThat(updated.getStreet_id()).isEqualTo(id);
            assertThat(updated.getName()).isEqualTo(newName);
            assertThat(updated.getCity_id()).isEqualTo(200);
        });

    }

    @Test
    void willNotUpdateWhenNoUpdate() {
        //Given
        Integer cityId=100;
        String name = "Tested9";
        Street street=new Street(
                9000,
                name,
                cityId
        );
        underTest.insertStreet(street);

        int id = underTest.selectAllStreets(cityId)
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(Street::getStreet_id)
                .findFirst()
                .orElseThrow();

        //When
        Street update= new Street();

        underTest.updateStreet(update);

        //Then
        Optional<Street> actual = underTest.selectStreetById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getStreet_id()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(street.getName());

            assertThat(c.getCity_id()).isEqualTo(street.getCity_id());
        });

    }
}