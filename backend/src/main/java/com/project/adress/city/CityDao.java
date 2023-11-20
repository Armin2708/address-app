package com.project.adress.city;

import java.util.List;
import java.util.Optional;

public interface CityDao {

    List<City> selectAllCities(Integer stateId);
    Optional<City> selectCityById(Integer id);
    void insertCity(City city);
    void deleteCityById(Integer id);
    boolean existCityById(Integer id);
    boolean existCityWithName(String name);
    boolean existStateWithStateId(Integer state_id);
    void updateCity(City city);
}
