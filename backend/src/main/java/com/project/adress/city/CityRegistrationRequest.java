package com.project.adress.city;

public record CityRegistrationRequest(
        Integer cityId,
    String cityName,
    Integer stateId
    )
{

}
