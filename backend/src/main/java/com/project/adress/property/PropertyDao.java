package com.project.adress.property;

import java.util.List;
import java.util.Optional;

public interface PropertyDao {

    List<Property> selectAllProperties();
    Optional<Property> selectPropertyById(Integer id);
    void insertProperty(Property property);
    void deletePropertyById(Integer id);
    boolean existPropertyById(Integer id);
    boolean existPropertyWithNumber(String number);
    boolean existStreetWithStreetId(Integer street_id);
    void updateProperty(Property property);
}
