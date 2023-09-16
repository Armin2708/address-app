package com.project.adress.street;

import com.project.adress.exception.DuplicateResourceException;
import com.project.adress.exception.RequestValidationException;
import com.project.adress.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreetService {

    private final StreetDao streetDao;

    public StreetService(@Qualifier("Streetjdbc") StreetDao streetDao){
        this.streetDao = streetDao;
    }
    public List<Street> getAllStreets(){return streetDao.selectAllStreets();}

    public Street getStreet(Integer id){
        return streetDao.selectStreetById(id)
                .orElseThrow(()-> new ResourceNotFoundException("street with id [%s] not found".formatted(id)));
    }

    public void addStreet(StreetRegistrationRequest streetRegistrationRequest){
        String name = streetRegistrationRequest.name();
        Integer id = streetRegistrationRequest.street_id();
        if (streetDao.existStreetById(id)){
            throw new DuplicateResourceException("street id already taken");
        }
        if (streetDao.existStreetWithName(name)){
            throw new DuplicateResourceException("street name already taken");
        }
        Integer cityId = streetRegistrationRequest.city_id();
        if (streetDao.existCityWithCityId(cityId)==false){
            throw new ResourceNotFoundException("city id does not exist");
        }
        Street street = new Street(
                streetRegistrationRequest.street_id(),
                streetRegistrationRequest.name(),
                streetRegistrationRequest.city_id()
        );
        streetDao.insertStreet(street);
    }

    public void deleteStreet(Integer id){
        if(!streetDao.existStreetById(id)){
            throw new ResourceNotFoundException("street with id [%s] not found".formatted(id));
        }
        streetDao.deleteStreetById(id);
    }

    public void updateStreet(Integer id, StreetUpdateRequest streetUpdateRequest){
        Street street = streetDao.selectStreetById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "street with id [%s] not found".formatted(id)
                ));
        boolean changes=false;

        if (streetUpdateRequest.name()!=null && !streetUpdateRequest.name().equals(street.getName())) {
            if(streetDao.existStreetWithName(streetUpdateRequest.name())) {
                throw new DuplicateResourceException("street name already taken");
            }
            street.setName(streetUpdateRequest.name());
            changes=true;
        }


        if (streetUpdateRequest.city_id()!=null && !streetUpdateRequest.city_id().equals(street.getCity_id())) {
            if (streetDao.existCityWithCityId(streetUpdateRequest.city_id())==false){
                throw new ResourceNotFoundException("city with id [%s] not found");
            }
            street.setCity_id(streetUpdateRequest.city_id());
            changes=true;
        }

        if(!changes) {
            throw new RequestValidationException("no data changes found");
        }

        streetDao.updateStreet(street);
    }
}
