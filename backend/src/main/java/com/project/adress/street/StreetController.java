package com.project.adress.street;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/countries/states/cities/streets")
public class StreetController {

    private final StreetService streetService;

    public StreetController(StreetService streetService){this.streetService = streetService;}

    @GetMapping
    public List<Street> getStreet(){return streetService.getAllStreets();}

    @GetMapping("{streetId}")
    public Street getStreet(
            @PathVariable("streetId") Integer streetId){
        return streetService.getStreet(streetId);
    }

    @PostMapping
    public void registerStreet(@RequestBody StreetRegistrationRequest request){
        streetService.addStreet(request);
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
