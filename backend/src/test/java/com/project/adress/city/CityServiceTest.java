package com.project.adress.city;

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
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityDao cityDao;
    CityService underTest;

    @BeforeEach
    void setUp() {
        underTest=new CityService(cityDao);
    }

    @Test
    void getAllCities() {

        //Given
        Integer stateId=10;

        //When
        underTest.getAllCities(stateId);

        //Then
        verify(cityDao).selectAllCities(stateId);
    }

    @Test
    void canGetCity() {

        //Given
        int id=100;
        City city = new City(id,
                "Test1",
                10);
        when(cityDao.selectCityById(id)).thenReturn(Optional.of(city));

        //When
        City actual = underTest.getCity(id);

        //Then
        assertThat(actual).isEqualTo(city);

    }

    @Test
    void willThrowWhenNotFound(){
        //Given
        int id = -1;

        when(cityDao.selectCityById(id)).thenReturn(Optional.empty());

        //When

        //Then
        assertThatThrownBy(() -> underTest.getCity(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("city with id [%s] not found".formatted(id));

    }

    @Test
    void willThrowWhenForeignKeyNotFound(){
        //Given
        Integer stateId=10;

        when(cityDao.existStateWithStateId(stateId)).thenReturn(false);
        CityRegistrationRequest cityRegistrationRequest= new CityRegistrationRequest(200,"Test2",10);
        City city = new City(
                200,
                "Test2",
                10);
        //When
        assertThatThrownBy(() ->underTest.addCity(cityRegistrationRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("state id does not exist");

        //Then
        verify(cityDao,never()).insertCity(city);

    }

    @Test
    void addCity() {

        //Given
        Integer id=300;
        Integer state_id = 10;
        when(cityDao.existStateWithStateId(state_id)).thenReturn(true);
        CityRegistrationRequest city = new CityRegistrationRequest(id,"Test3",state_id);

        //When
        underTest.addCity(city);

        //Then
        ArgumentCaptor<City> cityArgumentCaptor = ArgumentCaptor.forClass(
                City.class
        );
        verify(cityDao).insertCity(cityArgumentCaptor.capture());

        City capturedCity = cityArgumentCaptor.getValue();

        assertThat(capturedCity.getCity_id()).isEqualTo(300);
        assertThat(capturedCity.getName()).isEqualTo("Test3");
        assertThat(capturedCity.getState_id()).isEqualTo(10);

    }

    @Test
    void willThrowWhenCityIdExistsAlready() {

        //Given
        Integer id=400;
        when(cityDao.existCityById(id)).thenReturn(true);
        Integer state_id = 10;
        CityRegistrationRequest city = new CityRegistrationRequest(id,"Test4",state_id);

        //When
        assertThatThrownBy(() ->underTest.addCity(city))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("city id already taken");

        //Then

        verify(cityDao, never()).insertCity(any());
    }

    @Test
    void willThrowWhenCityNameExistsAlready() {

        //Given
        String name ="Test5";
        when(cityDao.existCityWithName(name)).thenReturn(true);
        Integer state_id = 10;
        CityRegistrationRequest city = new CityRegistrationRequest(500,name,state_id);

        //When
        assertThatThrownBy(() ->underTest.addCity(city))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("city name already taken");

        //Then

        verify(cityDao, never()).insertCity(any());
    }

    @Test
    void deleteCity() {

        //Given
        int id = 600;

        when(cityDao.existCityById(id)).thenReturn(true);

        //When
        underTest.deleteCity(id);

        //Then
        verify(cityDao).deleteCityById(id);

    }

    @Test
    void willThrowWhenDeleteCityIdNotExists() {

        //Given
        int id = -1;

        when(cityDao.existCityById(id)).thenReturn(false);

        //When

        assertThatThrownBy(() -> underTest.deleteCity(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("city with id [%s] not found".formatted(id));

        //Then
        verify(cityDao,never()).deleteCityById(id);
    }

    @Test
    void canUpdateAllCityProperties() {

        //Given
        int id = 700;
        City city = new City(id,"Test7",10);
        when(cityDao.selectCityById(id)).thenReturn(Optional.of(city));

        String newName = "Updated1";
        Integer newStateId = 20;
        when(cityDao.existStateWithStateId(newStateId)).thenReturn(true);

        CityUpdateRequest update = new CityUpdateRequest(newName,newStateId);

        when(cityDao.existCityWithName(newName)).thenReturn(false);

        //When
        underTest.updateCity(id,update);

        //Then
        ArgumentCaptor<City> cityArgumentCaptor = ArgumentCaptor.forClass(City.class);

        verify(cityDao).updateCity(cityArgumentCaptor.capture());
        City capturedCity = cityArgumentCaptor.getValue();

        assertThat(capturedCity.getName()).isEqualTo(update.name());
        assertThat(capturedCity.getState_id()).isEqualTo(update.state_id());

    }

    @Test
    void canOnlyUpdateCityName() {

        //Given
        int id = 800;
        City city = new City(id,"Test8",10);
        when(cityDao.selectCityById(id)).thenReturn(Optional.of(city));

        String newName = "Updated2";

        CityUpdateRequest update = new CityUpdateRequest(newName,null);

        when(cityDao.existCityWithName(newName)).thenReturn(false);

        //When
        underTest.updateCity(id,update);

        //Then
        ArgumentCaptor<City> cityArgumentCaptor = ArgumentCaptor.forClass(City.class);

        verify(cityDao).updateCity(cityArgumentCaptor.capture());
        City capturedCity = cityArgumentCaptor.getValue();

        assertThat(capturedCity.getName()).isEqualTo(update.name());
        assertThat(capturedCity.getState_id()).isEqualTo(city.getState_id());

    }

    @Test
    void canOnlyUpdateCityStateId() {

        //Given
        int id = 900;
        City city = new City(id,"Test9",10);
        when(cityDao.selectCityById(id)).thenReturn(Optional.of(city));

        Integer newStateId = 20;
        when(cityDao.existStateWithStateId(newStateId)).thenReturn(true);

        CityUpdateRequest update = new CityUpdateRequest(null,newStateId);

        //When
        underTest.updateCity(id,update);

        //Then
        ArgumentCaptor<City> cityArgumentCaptor = ArgumentCaptor.forClass(City.class);

        verify(cityDao).updateCity(cityArgumentCaptor.capture());
        City capturedCity = cityArgumentCaptor.getValue();

        assertThat(capturedCity.getName()).isEqualTo(city.getName());
        assertThat(capturedCity.getState_id()).isEqualTo(update.state_id());

    }

    @Test
    void willThrowUpdateWhenCityNameAlreadyTaken(){
        //Given
        int id = 1000;
        City city = new City(id,"Test10",10);
        when(cityDao.selectCityById(id)).thenReturn(Optional.of(city));

        String newName = "Updated3";
        when(cityDao.existCityWithName(newName)).thenReturn(true);

        CityUpdateRequest update = new CityUpdateRequest(newName,null);

        //When
        assertThatThrownBy(()->underTest.updateCity(id,update))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("city name already taken");

        //Then
        verify(cityDao,never()).updateCity(any());

    }

    @Test
    void willThrowUpdateWhenStateIdNotExists(){
        //Given
        int id = 1100;

        City city = new City(id,"Test11",10);
        when(cityDao.selectCityById(id)).thenReturn(Optional.of(city));

        Integer newStateId = -1;
        when(cityDao.existStateWithStateId(newStateId)).thenReturn(false);

        CityUpdateRequest update = new CityUpdateRequest(null,newStateId);

        //When
        assertThatThrownBy(()->underTest.updateCity(id,update))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("state with id [%s] not found");

        //Then
        verify(cityDao,never()).updateCity(any());

    }

    @Test
    void willThrowUpdateWhenNoChanges(){
        //Given
        int id = 1200;

        City city = new City(id,"Test12",10);
        when(cityDao.selectCityById(id)).thenReturn(Optional.of(city));

        CityUpdateRequest update = new CityUpdateRequest(city.getName(),city.getState_id());

        //When
        assertThatThrownBy(()->underTest.updateCity(id,update))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        //Then
        verify(cityDao,never()).updateCity(any());

    }
}