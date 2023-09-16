package com.project.adress.property;

public record PropertyRegistrationRequest(
        Integer property_id,
    String property_number,
    Integer street_id
    )
{

}
