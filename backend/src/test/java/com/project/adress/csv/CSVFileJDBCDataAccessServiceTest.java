/*package com.project.adress.csv;

import com.project.adress.AbstractTestContainer;
import com.project.adress.country.Country;
import com.project.adress.country.CountryJPADataAccessService;
import com.project.adress.country.CountryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.junit.jupiter.api.Assertions.*;

class CSVFileJDBCDataAccessServiceTest extends AbstractTestContainer {

    private CSVFileJDBCDataAccessService underTest;
    private CountryJPADataAccessService testing;

    private AutoCloseable autoCloseable;

    @Mock private CountryRepository countryRepository;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest= new CSVFileJDBCDataAccessService(getJdbcTemplate());
        testing=new CountryJPADataAccessService(countryRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void insertCSVLine() {
        // Given
        String tableName = "countries";
        String[] listToInsert = {"ROM","Romania"};

        // When
        underTest.batchCountry(listToInsert);

        // Retrieve the inserted data from the database
        Optional<Country> insertedCountry = testing.selectCountryById("ROM");

        // Then
        assertThat(insertedCountry).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo("ROM");
            assertThat(c.getName()).isEqualTo("Romania");
        });
    }
}
 */