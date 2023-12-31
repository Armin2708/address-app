package com.project.adress.property;

import com.project.adress.exception.DuplicateResourceException;
import com.project.adress.exception.RequestValidationException;
import com.project.adress.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private final PropertyDao propertyDao;

    public PropertyService(@Qualifier("Propertyjdbc") PropertyDao propertyDao){
        this.propertyDao = propertyDao;
    }
    public List<Property> getAllProperties(Integer streetId){return propertyDao.selectAllProperties(streetId);}

    public Property getProperty(Integer id){
        return propertyDao.selectPropertyById(id)
                .orElseThrow(()-> new ResourceNotFoundException("property with id [%s] not found".formatted(id)));
    }

    public void addProperty(PropertyRegistrationRequest propertyRegistrationRequest){
        String name = propertyRegistrationRequest.name();
        Integer id = propertyRegistrationRequest.property_id();
        Integer street_id = propertyRegistrationRequest.street_id();

        if (propertyDao.existPropertyById(id)){
            throw new DuplicateResourceException("property id already taken");
        }
        if (propertyDao.existPropertyWithName(name)){
            throw new DuplicateResourceException("property name already taken");
        }
        Integer streetId = propertyRegistrationRequest.street_id();
        if (propertyDao.existStreetWithStreetId(streetId)==false){
            throw new ResourceNotFoundException("street id does not exist");
        }
        Property property = new Property(
                propertyRegistrationRequest.property_id(),
                propertyRegistrationRequest.name(),
                propertyRegistrationRequest.street_id()
        );
        propertyDao.insertProperty(property);
    }

    public void deleteProperty(Integer id){
        if(!propertyDao.existPropertyById(id)){
            throw new ResourceNotFoundException("property with id [%s] not found".formatted(id));
        }
        propertyDao.deletePropertyById(id);
    }

    public void updateProperty(Integer id, PropertyUpdateRequest propertyUpdateRequest){
        Property property = propertyDao.selectPropertyById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "property with id [%s] not found".formatted(id)
                ));
        boolean changes=false;

        if (propertyUpdateRequest.name()!=null && !propertyUpdateRequest.name().equals(property.getName())) {
            if(propertyDao.existPropertyWithName(propertyUpdateRequest.name())) {
                throw new DuplicateResourceException("property name already taken");
            }
            property.setName(propertyUpdateRequest.name());
            changes=true;
        }

        if (propertyUpdateRequest.street_id()!=null && !propertyUpdateRequest.street_id().equals(property.getStreet_id())) {
            if (propertyDao.existStreetWithStreetId(propertyUpdateRequest.street_id())==false){
                throw new ResourceNotFoundException("street with id [%s] not found");
            }
            property.setStreet_id(propertyUpdateRequest.street_id());
            changes=true;
        }

        if(!changes) {
            throw new RequestValidationException("no data changes found");
        }

        propertyDao.updateProperty(property);
    }
}
