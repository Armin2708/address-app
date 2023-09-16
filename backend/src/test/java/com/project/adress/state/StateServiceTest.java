package com.project.adress.state;

import com.project.adress.exception.DuplicateResourceException;
import com.project.adress.exception.RequestValidationException;
import com.project.adress.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.registerCustomDateFormat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StateServiceTest {

    @Mock
    private StateDao stateDao;
    StateService underTest;

    @BeforeEach
    void setUp() {
        underTest=new StateService(stateDao);
    }

    @Test
    void getAllStates() {

        //Given

        //When
        underTest.getAllStates();

        //Then
        verify(stateDao).selectAllStates();
    }

    @Test
    void canGetState() {

        //Given
        int id=10;
        State state = new State(id,"California","FRA");
        when(stateDao.selectStateById(id)).thenReturn(Optional.of(state));

        //When
        State actual = underTest.getState(id);

        //Then
        assertThat(actual).isEqualTo(state);

    }

    @Test
    void willThrowWhenNotFound(){
        //Given
        int id = 10;

        when(stateDao.selectStateById(id)).thenReturn(Optional.empty());

        //When

        //Then
        assertThatThrownBy(() -> underTest.getState(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("state with id [%s] not found".formatted(id));

    }

    @Test
    void willThrowWhenForeignKeyNotFound(){
        //Given
        String countryId="XXX";

        when(stateDao.existCountryWithCountryId(countryId)).thenReturn(false);
        StateRegistrationRequest stateRegistrationRequest= new StateRegistrationRequest(10,"California","XXX");
        State state = new State(10,"California","XXX");
        //When
        assertThatThrownBy(() ->underTest.addState(stateRegistrationRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("country id does not exist");

        //Then
        verify(stateDao,never()).insertState(state);

    }

    @Test
    void addState() {

        //Given
        Integer id=10;
        String country_id = "FRA";
        when(stateDao.existCountryWithCountryId(country_id)).thenReturn(true);
        StateRegistrationRequest state = new StateRegistrationRequest(id,"state",country_id);

        //When
        underTest.addState(state);

        //Then
        ArgumentCaptor<State> stateArgumentCaptor = ArgumentCaptor.forClass(
                State.class
        );
        verify(stateDao).insertState(stateArgumentCaptor.capture());

        State capturedState = stateArgumentCaptor.getValue();

        assertThat(capturedState.getState_id()).isEqualTo(10);
        assertThat(capturedState.getName()).isEqualTo("state");
        assertThat(capturedState.getCountry_id()).isEqualTo("FRA");

    }

    @Test
    void willThrowWhenStateIdExistsAlready() {

        //Given
        Integer id=10;
        when(stateDao.existStateById(id)).thenReturn(true);
        String country_id = "XXX";
        StateRegistrationRequest state = new StateRegistrationRequest(id,"state",country_id);

        //When
        assertThatThrownBy(() ->underTest.addState(state))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("state id already taken");

        //Then

        verify(stateDao, never()).insertState(any());
    }

    @Test
    void willThrowWhenStateNameExistsAlready() {

        //Given
        String name ="State";
        when(stateDao.existStateWithName(name)).thenReturn(true);
        String country_id = "XXX";
        StateRegistrationRequest state = new StateRegistrationRequest(10,name,country_id);

        //When
        assertThatThrownBy(() ->underTest.addState(state))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("state name already taken");

        //Then

        verify(stateDao, never()).insertState(any());
    }

    @Test
    void deleteState() {

        //Given
        int id = 10;

        when(stateDao.existStateById(id)).thenReturn(true);

        //When
        underTest.deleteState(id);

        //Then
        verify(stateDao).deleteStateById(id);

    }

    @Test
    void willThrowWhenDeleteStateIdNotExists() {

        //Given
        int id = 10;

        when(stateDao.existStateById(id)).thenReturn(false);

        //When

        assertThatThrownBy(() -> underTest.deleteState(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("state with id [%s] not found".formatted(id));

        //Then
        verify(stateDao,never()).deleteStateById(id);
    }

    @Test
    void canUpdateAllStateProperties() {

        //Given
        int id = 10;
        State state = new State(id,"State","FRA");
        when(stateDao.selectStateById(id)).thenReturn(Optional.of(state));

        String newName = "California";
        String newCountryId = "XXX";
        when(stateDao.existCountryWithCountryId(newCountryId)).thenReturn(true);

        StateUpdateRequest update = new StateUpdateRequest(newName,newCountryId);

        when(stateDao.existStateWithName(newName)).thenReturn(false);

        //When
        underTest.updateState(id,update);

        //Then
        ArgumentCaptor<State> stateArgumentCaptor = ArgumentCaptor.forClass(State.class);

        verify(stateDao).updateState(stateArgumentCaptor.capture());
        State capturedState = stateArgumentCaptor.getValue();

        assertThat(capturedState.getName()).isEqualTo(update.name());
        assertThat(capturedState.getCountry_id()).isEqualTo(update.country_id());

    }

    @Test
    void canOnlyUpdateStateName() {

        //Given
        int id = 10;
        State state = new State(id,"State","FRA");
        when(stateDao.selectStateById(id)).thenReturn(Optional.of(state));

        String newName = "California";

        StateUpdateRequest update = new StateUpdateRequest(newName,null);

        when(stateDao.existStateWithName(newName)).thenReturn(false);

        //When
        underTest.updateState(id,update);

        //Then
        ArgumentCaptor<State> stateArgumentCaptor = ArgumentCaptor.forClass(State.class);

        verify(stateDao).updateState(stateArgumentCaptor.capture());
        State capturedState = stateArgumentCaptor.getValue();

        assertThat(capturedState.getName()).isEqualTo(update.name());
        assertThat(capturedState.getCountry_id()).isEqualTo(state.getCountry_id());

    }

    @Test
    void canOnlyUpdateStateCountryId() {

        //Given
        int id = 10;
        State state = new State(id,"State","FRA");
        when(stateDao.selectStateById(id)).thenReturn(Optional.of(state));

        String newCountryId = "CAL";
        when(stateDao.existCountryWithCountryId(newCountryId)).thenReturn(true);

        StateUpdateRequest update = new StateUpdateRequest(null,newCountryId);

        //When
        underTest.updateState(id,update);

        //Then
        ArgumentCaptor<State> stateArgumentCaptor = ArgumentCaptor.forClass(State.class);

        verify(stateDao).updateState(stateArgumentCaptor.capture());
        State capturedState = stateArgumentCaptor.getValue();

        assertThat(capturedState.getName()).isEqualTo(state.getName());
        assertThat(capturedState.getCountry_id()).isEqualTo(update.country_id());

    }

    @Test
    void willThrowUpdateWhenStateNameAlreadyTaken(){
        //Given
        int id = 10;
        State state = new State(id,"State","FRA");
        when(stateDao.selectStateById(id)).thenReturn(Optional.of(state));

        String newName = "California";
        when(stateDao.existStateWithName(newName)).thenReturn(true);

        StateUpdateRequest update = new StateUpdateRequest(newName,null);

        //When
        assertThatThrownBy(()->underTest.updateState(id,update))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("state name already taken");

        //Then
        verify(stateDao,never()).updateState(any());

    }

    @Test
    void willThrowUpdateWhenCountryIdNotExists(){
        //Given
        int id = 10;

        State state = new State(id,"State","XXX");
        when(stateDao.selectStateById(id)).thenReturn(Optional.of(state));

        String newCountryId = "FRA";
        when(stateDao.existCountryWithCountryId(newCountryId)).thenReturn(false);

        StateUpdateRequest update = new StateUpdateRequest(null,newCountryId);

        //When
        assertThatThrownBy(()->underTest.updateState(id,update))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("country with id [%s] not found");

        //Then
        verify(stateDao,never()).updateState(any());

    }

    @Test
    void willThrowUpdateWhenNoChanges(){
        //Given
        int id = 10;

        State state = new State(id,"State","FRA");
        when(stateDao.selectStateById(id)).thenReturn(Optional.of(state));

        StateUpdateRequest update = new StateUpdateRequest(state.getName(),state.getCountry_id());

        //When
        assertThatThrownBy(()->underTest.updateState(id,update))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        //Then
        verify(stateDao,never()).updateState(any());

    }
}