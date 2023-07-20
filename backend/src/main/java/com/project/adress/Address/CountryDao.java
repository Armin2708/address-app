package com.project.adress.Address;

import java.util.List;
import java.util.Optional;

public interface CountryDao {
    List<Country> selectAllCountries();
    Optional<Country> selectCountryById(String id);
    void insertCountry(Country country);

    boolean existCountryWithName(String name);
    boolean existCountryWithId(String id);

    void deleteCountryWithName(String name);
    void deleteCountryWithId(String id);

    void updateCountry(Country update);


}
