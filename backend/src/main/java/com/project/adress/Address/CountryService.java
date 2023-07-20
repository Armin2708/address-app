package com.project.adress.Address;

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

    public Country getCountry(String id) {
        return countryDao.selectCountryById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "country with id [%s] not found".formatted(id)
                ));
    }

    public void addCountry(CountryRegistrationRequest countryRegistrationRequest) {
        String name = countryRegistrationRequest.name();
        if (countryDao.existCountryWithName(name)) {
            throw new DuplicateResourceException("name already taken");
        } else {
            Country country = new Country(
                    countryRegistrationRequest.id(),
                    countryRegistrationRequest.name()
            );
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

        if (countryUpdateRequest.name() != null && !countryUpdateRequest.name().equals(country.getName())) {
            country.setName(countryUpdateRequest.name());
            changes = true;
        }
        if (countryUpdateRequest.id() != null && !countryUpdateRequest.id().equals(country.getId())) {
            country.setId(countryUpdateRequest.id());
            changes = true;
        }

        if (countryUpdateRequest.name()!=null && !countryUpdateRequest.name().equals(country.getName())) {
            if (countryDao.existCountryWithName(countryUpdateRequest.name())) {
                throw new DuplicateResourceException(
                        "name already taken");
            }
            country.setName(countryUpdateRequest.name());
            changes=true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        countryDao.updateCountry(country);
    }
}
