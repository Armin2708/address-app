package com.project.adress.state;

import com.project.adress.country.Country;

import java.util.Objects;

public class State{
    private String name;
    private Integer state_id;
    private String country_id;

   public State(Integer state_id,String name, String country_id){
       this.name=name;
       this.country_id = country_id;
       this.state_id = state_id;
   }

   public State(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public Integer getState_id() {
        return state_id;
    }

    public void setState_id(Integer state_id) {
        this.state_id = state_id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(country_id, state.country_id) && Objects.equals(name, state.name)  && Objects.equals(state_id, state.state_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state_id, name, country_id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + state_id +
                ", name='" + name + '\'' +
                ", countryKey=" + country_id + '\'' +
                '}';
    }
}
