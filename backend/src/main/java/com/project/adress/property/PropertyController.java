package com.project.adress.property;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/countries/states/cities/{streetId}/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService){this.propertyService = propertyService;}

    @GetMapping
    public List<Property> getProperties(
            @PathVariable String streetId){
        Integer streetIdInt = Integer.parseInt(streetId);
        return propertyService.getAllProperties(streetIdInt);}

    @GetMapping("{propertyId}")
    public Property getProperty(
            @PathVariable("propertyId") Integer propertyId){
        return propertyService.getProperty(propertyId);
    }

    @PostMapping
    public void registerProperty(
            @RequestBody PropertyRegistrationRequest request,
            @PathVariable String streetId){
        Integer streetIdInt = Integer.parseInt(streetId);
        PropertyRegistrationRequest propertyRegistrationRequest = new PropertyRegistrationRequest(
                request.property_id(),
                request.name(),
                streetIdInt
        );
        propertyService.addProperty(propertyRegistrationRequest);
    }

    @DeleteMapping("{propertyId}")
    public void deleteProperty(@PathVariable("propertyId") Integer propertyId){
        propertyService.deleteProperty(propertyId);
    }

    @PutMapping("{propertyId}")
    public void updateProperty(
            @PathVariable("propertyId") Integer propertyId,
            @RequestBody PropertyUpdateRequest updateRequest){
        propertyService.updateProperty(propertyId,updateRequest);
    }


}
