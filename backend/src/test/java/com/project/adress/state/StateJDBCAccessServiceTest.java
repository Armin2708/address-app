package com.project.adress.state;

import com.project.adress.AbstractTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;


class StateJDBCAccessServiceTest extends AbstractTestContainer {

    private StateJDBCAccessService underTest;
    private final StateRowMapper stateRowMapper = new StateRowMapper();

    @BeforeEach
    void setUp() {
        underTest=new StateJDBCAccessService(
                getJdbcTemplate(),
                stateRowMapper
        );
    }

    @Test
    void selectAllStates() {
        //Given
        State state = new State(
                100,
                "Tested1",
                "CT1"
        );
        underTest.insertState(state);

        //When
        List<State> actual = underTest.selectAllStates();

        //Then
        assertThat(actual).isNotEmpty();

    }

    @Test
    void selectStateById() {
        //Given
        String name = "Tested2";
        State state=new State(
               200,
                name,
                "CT1"
        );
        underTest.insertState(state);

        int id = underTest.selectAllStates()
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(State::getState_id)
                .findFirst()
                .orElseThrow();

        //When
        Optional<State> actual = underTest.selectStateById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c-> {
            assertThat(c.getState_id()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(state.getName());
            assertThat(c.getCountry_id()).isEqualTo(state.getCountry_id());
        });

    }

    @Test
    void willReturnEmptyWhenSelectStatesById(){
        //Given
        int id = -1;

        //When
        var actual = underTest.selectStateById(id);

        //Then
        assertThat(actual).isEmpty();
    }

    @Test
    void insertState() {
    }

    @Test
    void existsStateWithName(){

        //Given
        String name = "Tested3";
        State state=new State(
                300,
                name,
                "CT1"
        );
        underTest.insertState(state);

        //When
        boolean actual = underTest.existStateWithName(name);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsStateWithNameReturnFalseWhenNotExist(){

        //Given
        String name = "DoesNotExist";

        //When
        boolean actual = underTest.existStateWithName(name);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existStateById() {
        //Given
        String name = "Tested4";
        State state=new State(
                400,
                name,
                "CT1"
        );
        underTest.insertState(state);

        int id = underTest.selectAllStates()
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(State::getState_id)
                .findFirst()
                .orElseThrow();

        //When
        var actual = underTest.existStateById(id);

        //Then
        assertThat(actual).isTrue();

    }

    @Test
    void existsStateByIdWillReturnFalseWhenIdNotPresent(){

        //Given
        int id = -1;

        //When
        var actual = underTest.existStateById(id);

        //Then
        assertThat(actual).isFalse();

    }

    @Test
    void deleteStateById() {
        //Given
        String name = "Tested5";
        State state=new State(
                500,
                name,
                "CT1"
        );
        underTest.insertState(state);

        int id = underTest.selectAllStates()
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(State::getState_id)
                .findFirst()
                .orElseThrow();

        //When
        underTest.deleteStateById(id);

        //Then
        Optional<State> actual = underTest.selectStateById(id);
        assertThat(actual).isNotPresent();

    }

    @Test
    void existCountryWithCountryId() {
        //Given
        String country_id = "CT1";

        //When
        boolean actual = underTest.existCountryWithCountryId(country_id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existCountryWithCountryIdReturnFalseWhenNotExist() {
        //Given
        String country_id = "XXX";

        //When
        boolean actual = underTest.existCountryWithCountryId(country_id);

        //Then
        assertThat(actual).isFalse();

    }

    @Test
    void updateStateName() {
        //Given
        String name = "Tested6";
        State state = new State(
                600,
                name,
                "CT1"
        );
        underTest.insertState(state);

        int id = underTest.selectAllStates()
                .stream()
                .filter(c->c.getName().equals(name))
                .map(State::getState_id)
                .findFirst()
                .orElseThrow();

        var newName="Updated1";

        //When
        State update = new State();
        update.setState_id(id);
        update.setName(newName);
        underTest.updateState(update);

        //Then
        Optional<State> actual = underTest.selectStateById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c-> {
            assertThat(c.getState_id()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName);
            assertThat(c.getCountry_id()).isEqualTo(state.getCountry_id());
        });

    }

    @Test
    void updateStateCountryId() {
        //Given
        String name = "Tested7";
        State state = new State(
                700,
                name,
                "CT1"
        );
        underTest.insertState(state);

        int id = underTest.selectAllStates()
                .stream()
                .filter(c->c.getName().equals(name))
                .map(State::getState_id)
                .findFirst()
                .orElseThrow();

        var newCountryId="CT2";

        //When
        State update = new State();
        update.setState_id(id);
        update.setCountry_id(newCountryId);
        underTest.updateState(update);

        //Then
        Optional<State> actual = underTest.selectStateById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c-> {
            assertThat(c.getState_id()).isEqualTo(id);
            assertThat(c.getCountry_id()).isEqualTo(newCountryId);
            assertThat(c.getName()).isEqualTo(state.getName());
        });

    }

    @Test
    void updateAllStateProperties() {
        //Given
        String name = "Tested8";
        State state=new State(
                800,
                name,
                "CT1"
        );
        underTest.insertState(state);

        int id = underTest.selectAllStates()
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(State::getState_id)
                .findFirst()
                .orElseThrow();

        var newName="Updated2";


        //When
        State update= new State();
        update.setState_id(id);
        update.setName(newName);
        update.setCountry_id("CT2");

        underTest.updateState(update);

        //Then
        Optional<State> actual = underTest.selectStateById(id);

        assertThat(actual).isPresent().hasValueSatisfying(updated -> {
            assertThat(updated.getState_id()).isEqualTo(id);
            assertThat(updated.getName()).isEqualTo(newName);
            assertThat(updated.getCountry_id()).isEqualTo("CT2");
        });

    }

    @Test
    void willNotUpdateWhenNoUpdate() {
        //Given
        String name = "Tested9";
        State state=new State(
                900,
                name,
                "CT1"
        );
        underTest.insertState(state);

        int id = underTest.selectAllStates()
                .stream()
                .filter(c -> c.getName().equals(name))
                .map(State::getState_id)
                .findFirst()
                .orElseThrow();



        //When
        State update= new State();

        underTest.updateState(update);

        //Then
        Optional<State> actual = underTest.selectStateById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getState_id()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(state.getName());
            assertThat(c.getCountry_id()).isEqualTo(state.getCountry_id());
        });

    }
}