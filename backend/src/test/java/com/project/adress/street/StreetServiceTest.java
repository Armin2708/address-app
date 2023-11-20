package com.project.adress.street;

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
class StreetServiceTest {

    @Mock
    private StreetDao streetDao;
    StreetService underTest;

    @BeforeEach
    void setUp() {
        underTest=new StreetService(streetDao);
    }

    @Test
    void getAllStreets() {

        //Given
        Integer cityId=100;

        //When
        underTest.getAllStreets(cityId);

        //Then
        verify(streetDao).selectAllStreets(cityId);
    }

    @Test
    void canGetStreet() {

        //Given
        int id=1000;
        Street street = new Street(id,
                "Test1",
                100);
        when(streetDao.selectStreetById(id)).thenReturn(Optional.of(street));

        //When
        Street actual = underTest.getStreet(id);

        //Then
        assertThat(actual).isEqualTo(street);

    }

    @Test
    void willThrowWhenNotFound(){
        //Given
        int id = -1;

        when(streetDao.selectStreetById(id)).thenReturn(Optional.empty());

        //When

        //Then
        assertThatThrownBy(() -> underTest.getStreet(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("street with id [%s] not found".formatted(id));

    }

    @Test
    void willThrowWhenForeignKeyNotFound(){
        //Given
        Integer cityId=100;

        when(streetDao.existCityWithCityId(cityId)).thenReturn(false);
        StreetRegistrationRequest StreetRegistrationRequest= new StreetRegistrationRequest(2000,"Test2",100);
        Street street = new Street(
                2000,
                "Test2",
                100);
        //When
        assertThatThrownBy(() ->underTest.addStreet(StreetRegistrationRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("city id does not exist");

        //Then
        verify(streetDao,never()).insertStreet(street);

    }

    @Test
    void addStreet() {

        //Given
        Integer id=3000;
        Integer city_id = 100;
        when(streetDao.existCityWithCityId(city_id)).thenReturn(true);
        StreetRegistrationRequest Street = new StreetRegistrationRequest(id,"Test3",city_id);

        //When
        underTest.addStreet(Street);

        //Then
        ArgumentCaptor<Street> StreetArgumentCaptor = ArgumentCaptor.forClass(
                Street.class
        );
        verify(streetDao).insertStreet(StreetArgumentCaptor.capture());

        Street capturedStreet = StreetArgumentCaptor.getValue();

        assertThat(capturedStreet.getStreet_id()).isEqualTo(3000);
        assertThat(capturedStreet.getName()).isEqualTo("Test3");
        assertThat(capturedStreet.getCity_id()).isEqualTo(100);

    }

    @Test
    void willThrowWhenStreetIdExistsAlready() {

        //Given
        Integer id=4000;
        when(streetDao.existStreetById(id)).thenReturn(true);
        Integer city_id = 100;
        StreetRegistrationRequest Street = new StreetRegistrationRequest(id,"Test4",city_id);

        //When
        assertThatThrownBy(() ->underTest.addStreet(Street))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("street id already taken");

        //Then

        verify(streetDao, never()).insertStreet(any());
    }

    @Test
    void willThrowWhenStreetNameExistsAlready() {

        //Given
        String name ="Test5";
        when(streetDao.existStreetWithName(name)).thenReturn(true);
        Integer city_id = 100;
        StreetRegistrationRequest Street = new StreetRegistrationRequest(5000,name,city_id);

        //When
        assertThatThrownBy(() ->underTest.addStreet(Street))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("street name already taken");

        //Then

        verify(streetDao, never()).insertStreet(any());
    }

    @Test
    void deleteStreet() {

        //Given
        int id = 6000;

        when(streetDao.existStreetById(id)).thenReturn(true);

        //When
        underTest.deleteStreet(id);

        //Then
        verify(streetDao).deleteStreetById(id);

    }

    @Test
    void willThrowWhenDeleteStreetIdNotExists() {

        //Given
        int id = -1;

        when(streetDao.existStreetById(id)).thenReturn(false);

        //When

        assertThatThrownBy(() -> underTest.deleteStreet(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("street with id [%s] not found".formatted(id));

        //Then
        verify(streetDao,never()).deleteStreetById(id);
    }

    @Test
    void canUpdateAllStreetProperties() {

        //Given
        int id = 7000;
        Street street = new Street(id,"Test7",100);
        when(streetDao.selectStreetById(id)).thenReturn(Optional.of(street));

        String newName = "Updated1";
        Integer newCityId = 20;
        when(streetDao.existCityWithCityId(newCityId)).thenReturn(true);

        StreetUpdateRequest update = new StreetUpdateRequest(newName,newCityId);

        when(streetDao.existStreetWithName(newName)).thenReturn(false);

        //When
        underTest.updateStreet(id,update);

        //Then
        ArgumentCaptor<Street> StreetArgumentCaptor = ArgumentCaptor.forClass(Street.class);

        verify(streetDao).updateStreet(StreetArgumentCaptor.capture());
        Street capturedStreet = StreetArgumentCaptor.getValue();

        assertThat(capturedStreet.getName()).isEqualTo(update.name());
        assertThat(capturedStreet.getCity_id()).isEqualTo(update.city_id());

    }

    @Test
    void canOnlyUpdateStreetName() {

        //Given
        int id = 8000;
        Street street = new Street(id,"Test8",100);
        when(streetDao.selectStreetById(id)).thenReturn(Optional.of(street));

        String newName = "Updated2";

        StreetUpdateRequest update = new StreetUpdateRequest(newName,null);

        when(streetDao.existStreetWithName(newName)).thenReturn(false);

        //When
        underTest.updateStreet(id,update);

        //Then
        ArgumentCaptor<Street> StreetArgumentCaptor = ArgumentCaptor.forClass(Street.class);

        verify(streetDao).updateStreet(StreetArgumentCaptor.capture());
        Street capturedStreet = StreetArgumentCaptor.getValue();

        assertThat(capturedStreet.getName()).isEqualTo(update.name());
        assertThat(capturedStreet.getCity_id()).isEqualTo(street.getCity_id());

    }

    @Test
    void canOnlyUpdateStreetCityId() {

        //Given
        int id = 9000;
        Street street = new Street(id,"Test9",100);
        when(streetDao.selectStreetById(id)).thenReturn(Optional.of(street));

        Integer newCityId = 20;
        when(streetDao.existCityWithCityId(newCityId)).thenReturn(true);

        StreetUpdateRequest update = new StreetUpdateRequest(null,newCityId);

        //When
        underTest.updateStreet(id,update);

        //Then
        ArgumentCaptor<Street> StreetArgumentCaptor = ArgumentCaptor.forClass(Street.class);

        verify(streetDao).updateStreet(StreetArgumentCaptor.capture());
        Street capturedStreet = StreetArgumentCaptor.getValue();

        assertThat(capturedStreet.getName()).isEqualTo(street.getName());
        assertThat(capturedStreet.getCity_id()).isEqualTo(update.city_id());

    }

    @Test
    void willThrowUpdateWhenStreetNameAlreadyTaken(){
        //Given
        int id = 10000;
        Street street = new Street(id,"Test10",100);
        when(streetDao.selectStreetById(id)).thenReturn(Optional.of(street));

        String newName = "Updated3";
        when(streetDao.existStreetWithName(newName)).thenReturn(true);

        StreetUpdateRequest update = new StreetUpdateRequest(newName,null);

        //When
        assertThatThrownBy(()->underTest.updateStreet(id,update))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("street name already taken");

        //Then
        verify(streetDao,never()).updateStreet(any());

    }

    @Test
    void willThrowUpdateWhenCityIdNotExists(){
        //Given
        int id = 11000;

        Street street = new Street(id,"Test11",100);
        when(streetDao.selectStreetById(id)).thenReturn(Optional.of(street));

        Integer newCityId = -1;
        when(streetDao.existCityWithCityId(newCityId)).thenReturn(false);

        StreetUpdateRequest update = new StreetUpdateRequest(null,newCityId);

        //When
        assertThatThrownBy(()->underTest.updateStreet(id,update))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("city with id [%s] not found");

        //Then
        verify(streetDao,never()).updateStreet(any());

    }

    @Test
    void willThrowUpdateWhenNoChanges(){
        //Given
        int id = 12000;

        Street street = new Street(id,"Test12",100);
        when(streetDao.selectStreetById(id)).thenReturn(Optional.of(street));

        StreetUpdateRequest update = new StreetUpdateRequest(street.getName(),street.getCity_id());

        //When
        assertThatThrownBy(()->underTest.updateStreet(id,update))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        //Then
        verify(streetDao,never()).updateStreet(any());

    }
}