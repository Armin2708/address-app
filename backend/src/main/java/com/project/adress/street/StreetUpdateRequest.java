package com.project.adress.street;

public record StreetUpdateRequest(
        String name,
        Integer city_id
) {
}
