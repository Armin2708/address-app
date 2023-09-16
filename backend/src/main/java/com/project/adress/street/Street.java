package com.project.adress.street;

import java.util.Objects;

public class Street{
    private String name;
    private Integer street_id;
    private Integer city_id;

   public Street(Integer street_id,String name, Integer city_id){
       this.name=name;
       this.city_id = city_id;
       this.street_id = street_id;
   }

   public Street(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
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
        Street street = (Street) o;
        return Objects.equals(city_id, street.city_id) && Objects.equals(name, street.name) && Objects.equals(street_id, street.street_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street_id, name, city_id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + street_id +
                ", name='" + name + '\'' +
                ", city_id=" + city_id + '\'' +
                '}';
    }
}
