package com.project.adress.state;

import com.project.adress.country.CountryDao;

import java.util.List;
import java.util.Optional;

public interface StateDao {

    List<State> selectAllStates();
    Optional<State> selectStateById(Integer id);
    void insertState(State state);
    void deleteStateById(Integer id);
    boolean existStateById(Integer id);
    boolean existStateWithName(String name);
    boolean existCountryWithCountryId(String country_id);
    void updateState(State state);
}
