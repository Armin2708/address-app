package com.project.adress.street;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/countries/states/{cityId}/streets")
public class StreetController {

    private final StreetService streetService;

    public StreetController(StreetService streetService){this.streetService = streetService;}

    @GetMapping
    public List<Street> getStreets(
            @PathVariable String cityId){
        Integer cityIdInt = Integer.parseInt(cityId);
        return streetService.getAllStreets(cityIdInt);}

    @GetMapping("{streetId}")
    public Street getStreet(
            @PathVariable("streetId") Integer streetId){
        return streetService.getStreet(streetId);
    }

    @PostMapping
    public void registerStreet(
            @RequestBody StreetRegistrationRequest request,
            @PathVariable String cityId){
        Integer cityIdInt = Integer.parseInt(cityId);
        StreetRegistrationRequest streetRegistrationRequest = new StreetRegistrationRequest(
                request.street_id(),
                request.name(),
                cityIdInt
        );
        streetService.addStreet(streetRegistrationRequest);
    }

    @DeleteMapping("{streetId}")
    public void deleteStreet(@PathVariable("streetId") Integer streetId){
        streetService.deleteStreet(streetId);
    }

    @PutMapping("{streetId}")
    public void updateStreet(
            @PathVariable("streetId") Integer streetId,
            @RequestBody StreetUpdateRequest updateRequest){
        streetService.updateStreet(streetId,updateRequest);
    }


}
