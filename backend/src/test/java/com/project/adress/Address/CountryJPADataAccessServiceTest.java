package com.project.adress.Address;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class CountryJPADataAccessServiceTest {

    private CountryJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock private CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CountryJPADataAccessService(countryRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCountries() {
        //Given

        //When
        underTest.selectAllCountries();

        //Then
        verify(countryRepository)
                .findAll();
    }

    @Test
    void selectCountryById() {
        //Given
        String id = "FRA";

        //When
        underTest.selectCountryById(id);

        //Then
        verify(countryRepository).findById(id);
    }

    @Test
    void insertCountry() {
        //Given
        Country country = new Country("FRA","France");

        //When
        underTest.insertCountry(country);

        //Then
        verify(countryRepository).save(country);
    }

    @Test
    void existCountryWithName() {
        //Given
        String name = "France";

        //When
        underTest.existCountryWithName(name);

        //Then
        verify(countryRepository).existsCountryByName(name);
    }

    @Test
    void existCountryWithId() {
        //Given
        String id = "FRA";

        //When
        underTest.existCountryWithId(id);

        //Then
        verify(countryRepository).existsCountryById(id);
    }

    @Test
    void deleteCountryWithName() {
        //Given
        String name = "France";

        //When
        underTest.deleteCountryWithName(name);

        //Then
        verify(countryRepository).deleteCountryByName(name);
    }

    @Test
    void deleteCountryWithId() {
        //Given
        String id = "FRA";

        //When
        underTest.deleteCountryWithId(id);

        //Then
        verify(countryRepository).deleteById(id);
    }

    @Test
    void updateCountry() {
        //Given
        Country country = new Country("FRA", "France");

        //When
        underTest.updateCountry(country);

        //Then
        verify(countryRepository).save(country);
    }
}