package com.project.adress.city;

import com.project.adress.AbstractTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class CityJDBCAccessServiceTest extends AbstractTestContainer {

    private CityJDBCAccessService underTest;
    private final CityRowMapper cityRowMapper = new CityRowMapper();


    @BeforeEach
    void setUp() {
        underTest=new CityJDBCAccessService(
                getJdbcTemplate(),
                cityRowMapper
        );

    }

    @Test
    void selectAllCities() {
        //Given
        City city = new City(
                111,
                "Tested1",
                10
        );
        underTest.insertCity(city);

        //When
        List<City> actual = underTest.selectAllCities();

        //Then
        assertThat(actual).isNotEmpty();

    }

    @Test
    void selectCityById() {
        //Given
        String name = "Tested2";
        City city = new City(
                222,
                name,
                10
        );
        underTest.insertCity(city);

        int id = underTest.selectAllCities()
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(City::getCity_id)
                .findFirst()
                .orElseThrow();

        //When
        Optional<City> actual = underTest.selectCityById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c-> {
            assertThat(c.getCity_id()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(city.getName());
            assertThat(c.getState_id()).isEqualTo(city.getState_id());
        });

    }

    @Test
    void willReturnEmptyWhenSelectCitiesById(){
        //Given
        int id = -1;

        //When
        var actual = underTest.selectCityById(id);

        //Then
        assertThat(actual).isEmpty();
    }

    @Test
    void insertCity() {
    }

    @Test
    void existsCityWithName(){

        //Given
        String name = "Tested3";
        City city = new City(
                300,
                name,
                10
        );
        underTest.insertCity(city);

        //When
        boolean actual = underTest.existCityWithName(name);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCityWithNameReturnFalseWhenNotExist(){

        //Given
        String name = "DoesNotExist";

        //When
        boolean actual = underTest.existCityWithName(name);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existCityById() {
        //Given
        String name = "Tested4";
        City city = new City(
                400,
                name,
                10
        );
        underTest.insertCity(city);

        int id = underTest.selectAllCities()
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(City::getCity_id)
                .findFirst()
                .orElseThrow();

        //When
        var actual = underTest.existCityById(id);

        //Then
        assertThat(actual).isTrue();

    }

    @Test
    void existsCityByIdWillReturnFalseWhenIdNotPresent(){

        //Given
        int id = -1;

        //When
        var actual = underTest.existCityById(id);

        //Then
        assertThat(actual).isFalse();

    }

    @Test
    void deleteCityById() {
        //Given
        String name = "Tested5";
        City city = new City(
                500,
                name,
                10
        );
        underTest.insertCity(city);

        int id = underTest.selectAllCities()
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(City::getCity_id)
                .findFirst()
                .orElseThrow();

        //When
        underTest.deleteCityById(id);

        //Then
        Optional<City> actual = underTest.selectCityById(id);
        assertThat(actual).isNotPresent();

    }

    @Test
    void existStateWithStateId() {
        //Given
        Integer state_id = 10;

        //When
        boolean actual = underTest.existStateWithStateId(state_id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existStateWithStateIdReturnFalseWhenNotExist() {
        //Given
        Integer state_id = -1;

        //When
        boolean actual = underTest.existStateWithStateId(state_id);

        //Then
        assertThat(actual).isFalse();

    }

    @Test
    void updateCityName() {
        //Given
        String name = "Tested6";
        City city = new City(
                600,
                name,
                10
        );
        underTest.insertCity(city);

        int id = underTest.selectAllCities()
                .stream()
                .filter(c->c.getName().equals(name))
                .map(City::getCity_id)
                .findFirst()
                .orElseThrow();

        var newName="Updated1";

        //When
        City update = new City();
        update.setCity_id(id);
        update.setName(newName);
        underTest.updateCity(update);

        //Then
        Optional<City> actual = underTest.selectCityById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c-> {
            assertThat(c.getCity_id()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName);
            assertThat(c.getState_id()).isEqualTo(city.getState_id());
        });

    }

    @Test
    void updateCityStateId() {
        //Given
        String name = "Tested7";
        City city = new City(
                700,
                name,
                10
        );
        underTest.insertCity(city);

        int id = underTest.selectAllCities()
                .stream()
                .filter(c->c.getName().equals(name))
                .map(City::getCity_id)
                .findFirst()
                .orElseThrow();

        var newStateId=20;

        //When
        City update = new City();
        update.setCity_id(id);
        update.setState_id(newStateId);
        underTest.updateCity(update);

        //Then
        Optional<City> actual = underTest.selectCityById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c-> {
            assertThat(c.getCity_id()).isEqualTo(id);
            assertThat(c.getState_id()).isEqualTo(newStateId);
            assertThat(c.getName()).isEqualTo(city.getName());
        });

    }

    @Test
    void updateAllCityProperties() {
        //Given
        String name = "Tested8";
        City city=new City(
                800,
                name,
                10
        );
        underTest.insertCity(city);

        int id = underTest.selectAllCities()
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(City::getCity_id)
                .findFirst()
                .orElseThrow();

        String newName="Updated2";

        //When
        City update= new City();
        update.setCity_id(id);
        update.setName(newName);
        update.setState_id(20);

        underTest.updateCity(update);

        //Then
        Optional<City> actual = underTest.selectCityById(id);

        assertThat(actual).isPresent().hasValueSatisfying(updated -> {
            assertThat(updated.getCity_id()).isEqualTo(id);
            assertThat(updated.getName()).isEqualTo(newName);
            assertThat(updated.getState_id()).isEqualTo(20);
        });

    }

    @Test
    void willNotUpdateWhenNoUpdate() {
        //Given
        String name = "Tested9";
        City city=new City(
                900,
                name,
                10
        );
        underTest.insertCity(city);

        int id = underTest.selectAllCities()
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(City::getCity_id)
                .findFirst()
                .orElseThrow();

        //When
        City update= new City();

        underTest.updateCity(update);

        //Then
        Optional<City> actual = underTest.selectCityById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getCity_id()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(city.getName());

            assertThat(c.getState_id()).isEqualTo(city.getState_id());
        });

    }
}