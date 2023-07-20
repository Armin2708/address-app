package com.project.adress.Address;

import com.project.adress.AbstractTestContainer;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CountryRepositoryTest extends AbstractTestContainer {

    @Autowired
    private CountryRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void existsCountryByName() {

        //Given
        String name = FAKER.name().name();
        Country country = new Country(FAKER.name().name(),name);
        underTest.save(country);

        //When
        var actual = underTest.existsCountryByName(name);

        //Then
        assertThat(actual).isTrue();

    }

    @Test
    void existsCountryByNameFailsWhenNameNotPresent() {

        //Given
        String name = FAKER.name().name();

        //When
        var actual = underTest.existsCountryByName(name);

        //Then
        assertThat(actual).isFalse();

    }

    @Test
    void existsCountryById() {
        //Given
        String id = FAKER.name().name();
        Country country = new Country(id, FAKER.name().name());
        underTest.save(country);

        //When
        var actual = underTest.existsCountryById(id);

        //Then
        assertThat(actual).isTrue();

    }

    @Test
    void existsCountryByIdFailsWhenIdNotPresent() {
        //Given
        String id = FAKER.name().name();

        //When
        var actual = underTest.existsCountryById(id);

        //Then
        assertThat(actual).isFalse();

    }

}