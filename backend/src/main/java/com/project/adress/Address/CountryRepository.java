package com.project.adress.Address;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
    boolean existsCountryByName(String name);
    boolean existsCountryById(String id);
    void deleteCountryByName(String name);
}
