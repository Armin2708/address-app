package com.project.adress.property;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/countries/states/cities/streets/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService){this.propertyService = propertyService;}

    @GetMapping
    public List<Property> getProperty(){return propertyService.getAllProperties();}

    @GetMapping("{propertyId}")
    public Property getProperty(
            @PathVariable("propertyId") Integer propertyId){
        return propertyService.getProperty(propertyId);
    }

    @PostMapping
    public void registerProperty(@RequestBody PropertyRegistrationRequest request){
        propertyService.addProperty(request);
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
