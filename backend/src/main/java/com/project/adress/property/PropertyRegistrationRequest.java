package com.project.adress.property;

public record PropertyRegistrationRequest(
        Integer property_id,
    String name,
    Integer street_id
    )
{

}
