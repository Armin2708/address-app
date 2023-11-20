package com.project.adress.country;

import com.project.adress.exception.DuplicateResourceException;
import com.project.adress.exception.RequestValidationException;
import com.project.adress.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    private final CountryDao countryDao;

    public CountryService(@Qualifier("jpa") CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    public List<Country> getAllCountries() {
        return countryDao.selectAllCountries();
    }

    public Country getCountryById(String id) {
        return countryDao.selectCountryById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "country with id [%s] not found".formatted(id)
                ));
    }
    public Country getCountryByName(String name) {
        return countryDao.selectCountryByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "country with name [%s] not found".formatted(name)
                ));
    }


    public void addCountry(CountryRegistrationRequest request) {
        String name = request.countryName();
        String id = request.countryId();

        // Check if the country name already exists
        if (countryDao.existCountryWithName(name)) {
            throw new DuplicateResourceException("name already taken");
        }

        // Check if the country ID already exists
        else if (countryDao.existCountryWithId(id)) {
            throw new DuplicateResourceException("id already taken");
        }
        else {
            // Create and insert the country if it doesn't exist
            Country country = new Country(id, name);
            countryDao.insertCountry(country);
        }
    }

    public void deleteCountry(String id) {
        if (!countryDao.existCountryWithId(id)) {
            throw new ResourceNotFoundException("country with id [%s] not found".formatted(id));
        }
        countryDao.deleteCountryWithId(id);
    }

    public void updateCountry(String id, CountryUpdateRequest countryUpdateRequest) {
        Country country = countryDao.selectCountryById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "country with id [%s] not found".formatted(id)
                ));
        boolean changes = false;

        if (countryUpdateRequest.countryName() != null && !countryUpdateRequest.countryName().equals(country.getName())) {
            // Check if the new name is already taken
            if (countryDao.existCountryWithName(countryUpdateRequest.countryName())) {
                throw new DuplicateResourceException("name already taken");
            }
            country.setName(countryUpdateRequest.countryName());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        countryDao.updateCountry(country);
    }

}
