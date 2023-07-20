package com.project.adress.Address;

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
    void canGetCountry() {
        //Given
        String id = "FRA";
        Country country = new Country(id,"France");
        when(countryDao.selectCountryById(id)).thenReturn(Optional.of(country));

        //When
        Country actual = underTest.getCountry(id);

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
        assertThatThrownBy(()-> underTest.getCountry(id))
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
        CountryUpdateRequest updateRequest = new CountryUpdateRequest("GER",newName);

        //When
        underTest.updateCountry(id,updateRequest);

        //Then
        ArgumentCaptor<Country> countryArgumentCaptor =
                ArgumentCaptor.forClass(Country.class);

        verify(countryDao).updateCountry(countryArgumentCaptor.capture());
        Country capturedCountry = countryArgumentCaptor.getValue();

        assertThat(capturedCountry.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCountry.getId()).isEqualTo(updateRequest.id());
    }

    @Test
    void canUpdateOnlyCountryName(){
        //Given
        String id = "FRA";
        Country country = new Country(id,"France");
        when(countryDao.selectCountryById(id)).thenReturn(Optional.of(country));
        CountryUpdateRequest updateRequest = new CountryUpdateRequest("Germany","FRA");

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
    void canUpdateOnlyCountryId(){
        //Given
        String id = "FRA";
        Country country = new Country(id,"France");
        when(countryDao.selectCountryById(id)).thenReturn(Optional.of(country));
        CountryUpdateRequest updateRequest = new CountryUpdateRequest(null,"GER");

        //When
        underTest.updateCountry(id,updateRequest);

        //Then
        ArgumentCaptor<Country> countryArgumentCaptor = ArgumentCaptor.forClass(Country.class);

        verify(countryDao).updateCountry(countryArgumentCaptor.capture());
        Country capturedCountry = countryArgumentCaptor.getValue();

        assertThat(capturedCountry.getName()).isEqualTo(country.getName());
        assertThat(capturedCountry.getId()).isEqualTo(updateRequest.id());
    }

    @Test
    void willThrowWhenTryingToUpdateCountryNameWhenAlreadyTaken(){
        //Given
        String id = "FRA";
        Country country = new Country(id,"France");
        when(countryDao.selectCountryById(id)).thenReturn(Optional.of(country));

        String newName = "Germany";

        CountryUpdateRequest updateRequest = new CountryUpdateRequest(newName, null);

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
                country.getName(),country.getId());

        // When
        assertThatThrownBy(() -> underTest.updateCountry(id, updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        // Then
        verify(countryDao, never()).updateCountry(any());
    }
}