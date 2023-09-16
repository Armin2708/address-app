package com.project.adress.country;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(
        name = "countries",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "country_name_unique",
                        columnNames = "name"
                )
        }
)
public class Country {
    @Id
    @Column(name = "country_id" )
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    public Country() {
    }

    public Country(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country country)) return false;
        return id.equals(country.id) && name.equals(country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
