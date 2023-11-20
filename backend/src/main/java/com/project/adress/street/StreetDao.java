package com.project.adress.street;

import java.util.List;
import java.util.Optional;

public interface StreetDao {

    List<Street> selectAllStreets(Integer cityId);
    Optional<Street> selectStreetById(Integer id);
    void insertStreet(Street street);
    void deleteStreetById(Integer id);
    boolean existStreetById(Integer id);
    boolean existStreetWithName(String name);
    boolean existCityWithCityId(Integer city_id);
    void updateStreet(Street street);
}
