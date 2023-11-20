package com.project.adress.property;

import java.util.Objects;

public class Property {

    private Integer property_id;
    private String name;
    private Integer street_id;

   public Property(Integer property_id, String name, Integer street_id){
       this.name=name;
       this.street_id = street_id;
       this.property_id = property_id;
   }

   public Property(){}

    public Integer getProperty_id() {
        return property_id;
    }

    public void setProperty_id(Integer property_id) {
        this.property_id = property_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStreet_id() {
        return street_id;
    }

    public void setStreet_id(Integer street_id) {
        this.street_id = street_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(street_id, property.street_id) && Objects.equals(name, property.name)&& Objects.equals(property_id, property.property_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(property_id, name, street_id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + property_id +
                ", name='" + name + '\'' +
                ", streetKey=" + street_id + '\'' +
                '}';
    }
}
