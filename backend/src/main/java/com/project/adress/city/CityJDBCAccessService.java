package com.project.adress.city;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("Cityjdbc")
public class CityJDBCAccessService implements CityDao {

    private final JdbcTemplate jdbcTemplate;
    private final CityRowMapper cityRowMapper;

    public CityJDBCAccessService(JdbcTemplate jdbcTemplate, CityRowMapper cityRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.cityRowMapper = cityRowMapper;
    }

    @Override
    public List<City> selectAllCities() {
        var sql = """
                 SELECT state_id,name,city_id
                 FROM cities
                 """;
        return jdbcTemplate.query(sql, cityRowMapper);
    }

    @Override
    public Optional<City> selectCityById(Integer id) {
        var sql = """
                SELECT state_id,name,city_id
                FROM cities
                WHERE city_id=?
                """;
        return jdbcTemplate.query(sql, cityRowMapper,id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCity(City city) {
        var sql = """
                INSERT INTO cities(city_id,name,state_id)
                VALUES(?,?,?)
                """;
        int result = jdbcTemplate.update(
                sql,
                city.getCity_id(),
                city.getName(),
                city.getState_id()
        );
        System.out.println("jdbcTemplate.update = "+result);

    }

    @Override
    public void deleteCityById(Integer id) {
        var sql = """
                DELETE 
                FROM cities
                WHERE city_id = ?
                """;
        Integer result = jdbcTemplate.update(sql,id);
        System.out.println("deleteCityById result = "+ result);

    }

    @Override
    public boolean existCityById(Integer id) {
        var sql = """
                SELECT count(city_id)
                FROM cities
                WHERE city_id=?
                """;
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,id);
        return count !=null &&count>0;
    }

    @Override
    public boolean existCityWithName(String name) {
        var sql = """
                SELECT count(city_id)
                FROM cities
                WHERE name=?
                """;
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,name);
        return count !=null &&count>0;
    }

    @Override
    public boolean existStateWithStateId(Integer state_id) {
        var sql = """
                SELECT count(state_id)
                FROM states
                WHERE state_id=?
                """;
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,state_id);
        return count !=null &&count>0;
    }

    @Override
    public void updateCity(City update) {
        if(update.getName()!=null){
            String sql = "UPDATE cities SET name = ? WHERE city_id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getName(),
                    update.getCity_id()
            );
            System.out.println("update city name result = " +result);
        }

        if (update.getState_id() != null) {
            // Check if the provided state_id exists in the states table
            boolean stateExists = existStateWithStateId(update.getState_id());

            if (stateExists) {
                // Perform the update with the valid state_id
                String sql = "UPDATE cities SET state_id = ? WHERE city_id = ?";
                int result = jdbcTemplate.update(
                        sql,
                        update.getState_id(),
                        update.getCity_id()
                );
                System.out.println("update city state result = " + result);
            } else {
                // Handle the case where the state_id is not valid
                System.out.println("Invalid state_id: " + update.getState_id());
            }
        }
    }
}
