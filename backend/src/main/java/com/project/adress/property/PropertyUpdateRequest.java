package com.project.adress.property;

public record PropertyUpdateRequest(
        String name,
        Integer street_id
) {
}