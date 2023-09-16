package com.project.adress.country;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, String> {
    boolean existsCountryByName(String name);
    boolean existsCountryById(String id);
    void deleteCountryByName(String name);
    Optional<Country> findByName(String name);
}
