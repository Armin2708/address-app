package com.project.adress.state;

public record StateUpdateRequest(
        String name,
        String country_id
) {
}
