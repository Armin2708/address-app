package com.project.adress.country;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @Mock
    private CountryDao countryDao;
    private CountryService underTest;

    @BeforeEach
    void setup(){underTest= new CountryService(countryDao);}

    @Test
    void getAllCountries() {
        //Given


        //When
        underTest.getAllCountries();

        //Then
        verify(countryDao).selectAllCountries();

    }

    @Test
    void canGetCountryById() {
        //Given
        String id = "FRA";
        Country country = new Country(id,"France");
        when(countryDao.selectCountryById(id)).thenReturn(Optional.of(country));

        //When
        Country actual = underTest.getCountryById(id);

        //Then
        assertThat(actual).isEqualTo(country);

    }

    @Test
    void canGetCountryByName() {
        //Given
        String name = "France";
        Country country = new Country("FRA",name);
        when(countryDao.selectCountryByName(name)).thenReturn(Optional.of(country));

        //When
        Country actual = underTest.getCountryByName(name);

        //Then
        assertThat(actual).isEqualTo(country);

    }

    @Test
    void willThrowWhenGetCountryReturnEmptyOptional(){
        //Given
        String id = "FRA";
        when(countryDao.selectCountryById(id)).thenReturn(Optional.empty());

        //When


        //Then
        assertThatThrownBy(()-> underTest.getCountryById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("country with id [%s] not found".formatted(id));

    }

    @Test
    void addCountry() {
        //Given
        String name = "France";

        when(countryDao.existCountryWithName(name)).thenReturn(false);

        CountryRegistrationRequest request = new CountryRegistrationRequest(name,"FRA");

        //When
        underTest.addCountry(request);

        //Then
        ArgumentCaptor<Country> countryArgumentCaptor = ArgumentCaptor.forClass(
                Country.class
        );

        verify(countryDao).insertCountry(countryArgumentCaptor.capture());

        Country capturedCountry = countryArgumentCaptor.getValue();

        assertThat(capturedCountry.getId()).isEqualTo(request.id());
        assertThat(capturedCountry.getName()).isEqualTo(request.name());

    }

    @Test
    void willThrowWhenTryingToAddCountryWithDuplicateName() {
        // Given
        String name = "France";
        String id = "FRA";

        // Mock that a country with the given name already exists
        when(countryDao.existCountryWithName(name)).thenReturn(true);

        CountryRegistrationRequest request = new CountryRegistrationRequest(name, id);

        // When and Then
        assertThatThrownBy(() -> underTest.addCountry(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("name already taken");
    }

    @Test
    void willThrowWhenTryingToAddCountryWithDuplicateId() {
        // Given
        String name = "France";
        String id = "FRA";

        when(countryDao.existCountryWithId(id)).thenReturn(true);
        // Mock that a country with the given id already exists

        CountryRegistrationRequest request = new CountryRegistrationRequest(name, id);

        // When and Then
        assertThatThrownBy(() -> underTest.addCountry(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("id already taken");
    }

    @Test
    void deleteCountryById() {
        //Given
        String id = "FRA";

        when(countryDao.existCountryWithId(id)).thenReturn(true);
        //When
        underTest.deleteCountry(id);

        //Then
        verify(countryDao).deleteCountryWithId(id);

    }

    @Test
    void willThrowDeleteCountryByIdNotExists(){
        //Given
        String id = "FRA";

        when(countryDao.existCountryWithId(id)).thenReturn(false);

        //When
        assertThatThrownBy(()-> underTest.deleteCountry(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("country with id [%s] not found".formatted(id));

        //Then
        verify(countryDao, never()).deleteCountryWithId(id);
    }

    @Test
    void canUpdateAllCountryProperties() {
        //Given
        String id = "FRA";
        Country country = new Country(id,"France");
        when(countryDao.selectCountryById(id)).thenReturn(Optional.of(country));

        String newName = "Germany";
        CountryUpdateRequest updateRequest = new CountryUpdateRequest(newName);

        //When
        underTest.updateCountry(id,updateRequest);

        //Then
        ArgumentCaptor<Country> countryArgumentCaptor =
                ArgumentCaptor.forClass(Country.class);

        verify(countryDao).updateCountry(countryArgumentCaptor.capture());
        Country capturedCountry = countryArgumentCaptor.getValue();

        assertThat(capturedCountry.getName()).isEqualTo(updateRequest.name());
    }

    @Test
    void canUpdateOnlyCountryName(){
        //Given
        String id = "FRA";
        Country country = new Country(id,"France");
        when(countryDao.selectCountryById(id)).thenReturn(Optional.of(country));
        CountryUpdateRequest updateRequest = new CountryUpdateRequest("Germany");

        //When
        underTest.updateCountry(id,updateRequest);

        //Then
        ArgumentCaptor<Country> countryArgumentCaptor = ArgumentCaptor.forClass(Country.class);

        verify(countryDao).updateCountry(countryArgumentCaptor.capture());
        Country capturedCountry = countryArgumentCaptor.getValue();

        assertThat(capturedCountry.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCountry.getId()).isEqualTo(country.getId());


    }

    @Test
    void willThrowWhenTryingToUpdateCountryNameWhenAlreadyTaken(){
        //Given
        String id = "FRA";
        Country country = new Country(id,"France");
        when(countryDao.selectCountryById(id)).thenReturn(Optional.of(country));

        String newName = "Germany";

        CountryUpdateRequest updateRequest = new CountryUpdateRequest(newName);

        when(countryDao.existCountryWithName(newName)).thenReturn(true);

        //When
        assertThatThrownBy(()-> underTest.updateCountry(id,updateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("name already taken");

        //Then
        verify(countryDao,never()).updateCountry(any());
    }

    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        // Given
        String id = "FRA";
        Country country = new Country(
                id, "France"
        );
        when(countryDao.selectCountryById(id)).thenReturn(Optional.of(country));

        CountryUpdateRequest updateRequest = new CountryUpdateRequest(
                country.getName());

        // When
        assertThatThrownBy(() -> underTest.updateCountry(id, updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        // Then
        verify(countryDao, never()).updateCountry(any());
    }
}