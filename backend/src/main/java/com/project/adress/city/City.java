package com.project.adress.city;

import java.util.Objects;

public class City{
    private Integer city_id;
    private String name;
    private Integer state_id;

   public City(Integer city_id,String name, Integer state_id){
       this.name=name;
       this.state_id = state_id;
       this.city_id = city_id;
   }

   public City(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState_id() {
        return state_id;
    }

    public void setState_id(Integer state_id) {
        this.state_id = state_id;
    }

    public Integer getCity_id() {
        return city_id;
    }
    public void setCity_id(int city_id) {
        this.city_id=city_id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(state_id, city.state_id) && Objects.equals(name, city.name) && Objects.equals(city_id, city.city_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city_id, name, state_id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + city_id +
                ", name='" + name + '\'' +
                ", stateKey=" + state_id + '\'' +
                '}';
    }

}
