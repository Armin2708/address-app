package com.project.adress.city;

public record CityUpdateRequest(
        String name,
        Integer state_id
) {
}
