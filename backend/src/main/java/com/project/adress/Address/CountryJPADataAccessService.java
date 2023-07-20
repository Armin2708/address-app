package com.project.adress.Address;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class CountryJPADataAccessService implements CountryDao {

    private final CountryRepository countryRepository;

    public CountryJPADataAccessService(CountryRepository countryRepository){
        this.countryRepository = countryRepository;
    }


    @Override
    public List<Country> selectAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<Country> selectCountryById(String id) {
        return countryRepository.findById(id);
    }

    @Override
    public void insertCountry(Country country) {
        countryRepository.save(country);

    }

    @Override
    public boolean existCountryWithName(String name) {
        return countryRepository.existsCountryByName(name);
    }

    @Override
    public boolean existCountryWithId(String id) {
        return countryRepository.existsCountryById(id);
    }

    @Override
    public void deleteCountryWithName(String name) {
        countryRepository.deleteCountryByName(name);
    }

    @Override
    public void deleteCountryWithId(String id) {
        countryRepository.deleteById(id);
    }

    @Override
    public void updateCountry(Country update) {
        countryRepository.save(update);
    }
}
