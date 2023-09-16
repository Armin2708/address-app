package com.project.adress.property;

public record PropertyUpdateRequest(
        String property_number,
        Integer street_id
) {
}