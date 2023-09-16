package com.project.adress.country;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService){this.countryService=countryService;}

    @GetMapping
    public List<Country> getCountries(){return countryService.getAllCountries();}

    @GetMapping("{countryId}")
    public Country getCountryById(@PathVariable("countryId") String countryId){
        return countryService.getCountryById(countryId);
    }

    @GetMapping("{name}")
    public Country getCountryByName(@PathVariable("name") String name){
        return countryService.getCountryByName(name);
    }

    @PostMapping
    public void registerCountry(@RequestBody CountryRegistrationRequest request){
        countryService.addCountry(request);
    }

    @DeleteMapping("{countryId}")
    public void deleteCountry(@PathVariable("countryId") String countryId){
        countryService.deleteCountry(countryId);
    }


    @PutMapping("{countryId}")
    public void updateCountry(
            @PathVariable("countryId") String id,
            @RequestBody CountryUpdateRequest request){
        countryService.updateCountry(id,request);
    }
}

