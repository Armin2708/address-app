package com.project.adress.city;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/countries/{stateId}/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService){this.cityService = cityService;}

    @GetMapping
    public List<City> getCities(
            @PathVariable String stateId
    ){
        Integer stateIdInt = Integer.parseInt(stateId);
        return cityService.getAllCities(stateIdInt);}

    @GetMapping("{cityId}")
    public City getCity(
            @PathVariable("cityId") Integer cityId){
        return cityService.getCity(cityId);
    }

    @PostMapping
    public void registerCity(
            @RequestBody CityRegistrationRequest request,
            @PathVariable String stateId){
        Integer stateIdInt = Integer.parseInt(stateId);
        CityRegistrationRequest cityRegistrationRequest = new CityRegistrationRequest(
                request.cityId(),
                request.cityName(),
                stateIdInt
        );
        cityService.addCity(cityRegistrationRequest);
    }

    @DeleteMapping("{cityId}")
    public void deleteCity(
            @PathVariable("cityId") Integer cityId){
        cityService.deleteCity(cityId);
    }

    @PutMapping("{cityId}")
    public void updateCity(
            @PathVariable("cityId") Integer cityId,
            @RequestBody CityUpdateRequest updateRequest){
        cityService.updateCity(cityId,updateRequest);
    }


}
