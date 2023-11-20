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
    public List<City> getAllCities(Integer stateId){return cityDao.selectAllCities(stateId);}

    public City getCity(Integer id){
        return cityDao.selectCityById(id)
                .orElseThrow(()-> new ResourceNotFoundException("city with id [%s] not found".formatted(id)));
    }

    public void addCity(CityRegistrationRequest cityRegistrationRequest){
        String name = cityRegistrationRequest.cityName();
        Integer id = cityRegistrationRequest.cityId();
        if (cityDao.existCityById(id)){
            throw new DuplicateResourceException("city id already taken");
        }
        if (cityDao.existCityWithName(name)){
            throw new DuplicateResourceException("city name already taken");
        }
        Integer stateId = cityRegistrationRequest.stateId();
        if (cityDao.existStateWithStateId(stateId)==false){
            throw new ResourceNotFoundException("state id does not exist");
        }
        City city = new City(
                cityRegistrationRequest.cityId(),
                cityRegistrationRequest.cityName(),
                cityRegistrationRequest.stateId()
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


        if(!changes) {
            throw new RequestValidationException("no data changes found");
        }

        cityDao.updateCity(city);
    }
}
