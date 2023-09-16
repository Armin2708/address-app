package com.project.adress.country;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        String id = "CT1";

        //When
        underTest.selectCountryById(id);

        //Then
        verify(countryRepository).findById(id);
    }

    @Test
    void insertCountry() {
        //Given
        Country country = new Country("CT1","CountryTest1");

        //When
        underTest.insertCountry(country);

        //Then
        verify(countryRepository).save(country);
    }

    @Test
    void existCountryWithName() {
        //Given
        String name = "CountryTest1";

        //When
        underTest.existCountryWithName(name);

        //Then
        verify(countryRepository).existsCountryByName(name);
    }

    @Test
    void existCountryWithId() {
        //Given
        String id = "CT1";

        //When
        underTest.existCountryWithId(id);

        //Then
        verify(countryRepository).existsCountryById(id);
    }

    @Test
    void deleteCountryWithName() {
        //Given
        String name = "CountryTest1";

        //When
        underTest.deleteCountryWithName(name);

        //Then
        verify(countryRepository).deleteCountryByName(name);
    }

    @Test
    void deleteCountryWithId() {
        //Given
        String id = "CT1";

        //When
        underTest.deleteCountryWithId(id);

        //Then
        verify(countryRepository).deleteById(id);
    }

    @Test
    void updateCountry() {
        //Given
        Country country = new Country("CT1", "CountryTest1");

        //When
        underTest.updateCountry(country);

        //Then
        verify(countryRepository).save(country);
    }
}