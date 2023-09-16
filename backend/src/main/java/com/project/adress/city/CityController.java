package com.project.adress.city;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/countries/states/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService){this.cityService = cityService;}

    @GetMapping
    public List<City> getCity(){return cityService.getAllCities();}

    @GetMapping("{cityId}")
    public City getCity(
            @PathVariable("cityId") Integer cityId){
        return cityService.getCity(cityId);
    }

    @PostMapping
    public void registerCity(@RequestBody CityRegistrationRequest request){
        cityService.addCity(request);
    }

    @DeleteMapping("{cityId}")
    public void deleteCity(@PathVariable("cityId") Integer cityId){
        cityService.deleteCity(cityId);
    }

    @PutMapping("{cityId}")
    public void updateCity(
            @PathVariable("cityId") Integer cityId,
            @RequestBody CityUpdateRequest updateRequest){
        cityService.updateCity(cityId,updateRequest);
    }


}
