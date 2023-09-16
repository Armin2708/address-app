package com.project.adress.city;

import com.project.adress.exception.DuplicateResourceException;
import com.project.adress.exception.RequestValidationException;
import com.project.adress.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityDao cityDao;

    public CityService(@Qualifier("Cityjdbc") CityDao cityDao){
        this.cityDao = cityDao;
    }
    public List<City> getAllCities(){return cityDao.selectAllCities();}

    public City getCity(Integer id){
        return cityDao.selectCityById(id)
                .orElseThrow(()-> new ResourceNotFoundException("city with id [%s] not found".formatted(id)));
    }

    public void addCity(CityRegistrationRequest cityRegistrationRequest){
        String name = cityRegistrationRequest.name();
        Integer id = cityRegistrationRequest.city_id();
        if (cityDao.existCityById(id)){
            throw new DuplicateResourceException("city id already taken");
        }
        if (cityDao.existCityWithName(name)){
            throw new DuplicateResourceException("city name already taken");
        }
        Integer stateId = cityRegistrationRequest.state_id();
        if (cityDao.existStateWithStateId(stateId)==false){
            throw new ResourceNotFoundException("state id does not exist");
        }
        City city = new City(
                cityRegistrationRequest.city_id(),
                cityRegistrationRequest.name(),
                cityRegistrationRequest.state_id()
        );
        cityDao.insertCity(city);
    }

    public void deleteCity(Integer id){
        if(!cityDao.existCityById(id)){
            throw new ResourceNotFoundException("city with id [%s] not found".formatted(id));
        }
        cityDao.deleteCityById(id);
    }

    public void updateCity(Integer id, CityUpdateRequest cityUpdateRequest){
        City city = cityDao.selectCityById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "city with id [%s] not found".formatted(id)
                ));
        boolean changes=false;

        if (cityUpdateRequest.name()!=null && !cityUpdateRequest.name().equals(city.getName())) {
            if(cityDao.existCityWithName(cityUpdateRequest.name())) {
                throw new DuplicateResourceException("city name already taken");
            }
            city.setName(cityUpdateRequest.name());
            changes=true;
        }

        if (cityUpdateRequest.state_id()!=null && !cityUpdateRequest.state_id().equals(city.getState_id())) {
            if (cityDao.existStateWithStateId(cityUpdateRequest.state_id())==false){
                throw new ResourceNotFoundException("state with id [%s] not found");
            }
            city.setState_id(cityUpdateRequest.state_id());
            changes=true;
        }

        if(!changes) {
            throw new RequestValidationException("no data changes found");
        }

        cityDao.updateCity(city);
    }
}
