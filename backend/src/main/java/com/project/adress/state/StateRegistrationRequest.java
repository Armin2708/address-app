package com.project.adress.state;

public record StateRegistrationRequest(
        Integer state_id,
    String name,
    String country_id
    )
{

}
