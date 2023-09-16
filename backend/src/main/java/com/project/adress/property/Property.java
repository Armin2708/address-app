package com.project.adress.property;

import java.util.Objects;

public class Property {

    private Integer property_id;
    private String property_number;
    private Integer street_id;

   public Property(Integer property_id, String property_number, Integer street_id){
       this.property_number=property_number;
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

    public String getProperty_number() {
        return property_number;
    }

    public void setProperty_number(String property_number) {
        this.property_number = property_number;
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
        return Objects.equals(street_id, property.street_id) && Objects.equals(property_number, property.property_number)&& Objects.equals(property_id, property.property_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(property_id, property_number, street_id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + property_id +
                ", number='" + property_number + '\'' +
                ", streetKey=" + street_id + '\'' +
                '}';
    }
}
