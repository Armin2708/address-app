package com.project.adress.state;

public record StateRegistrationRequest(
        Integer stateId,
    String stateName,
    String countryId
    )
{

}
