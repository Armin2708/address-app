package com.project.adress.state;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("Statejdbc")
public class StateJDBCAccessService implements StateDao {

    private final JdbcTemplate jdbcTemplate;
    private final StateRowMapper stateRowMapper;

    public StateJDBCAccessService(JdbcTemplate jdbcTemplate, StateRowMapper stateRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.stateRowMapper = stateRowMapper;
    }

    @Override
    public List<State> selectAllStates() {
        var sql = """
                 SELECT country_id,name,state_id
                 FROM states
                 """;
        return jdbcTemplate.query(sql, stateRowMapper);
    }

    @Override
    public Optional<State> selectStateById(Integer id) {
        var sql = """
                SELECT country_id,name,state_id
                FROM states
                WHERE state_id=?
                """;
        return jdbcTemplate.query(sql, stateRowMapper,id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertState(State state) {
        var sql = """
                INSERT INTO states(state_id,name,country_id)
                VALUES(?,?,?)
                """;
        int result = jdbcTemplate.update(
                sql,
                state.getState_id(),
                state.getName(),
                state.getCountry_id()
        );
        System.out.println("jdbcTemplate.update = "+result);

    }

    @Override
    public void deleteStateById(Integer id) {
        var sql = """
                DELETE 
                FROM states
                WHERE state_id = ?
                """;
        Integer result = jdbcTemplate.update(sql,id);
        System.out.println("deleteStateById result = "+ result);

    }

    @Override
    public boolean existStateById(Integer id) {
        var sql = """
                SELECT count(state_id)
                FROM states
                WHERE state_id=?
                """;
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,id);
        return count !=null &&count>0;
    }

    @Override
    public boolean existStateWithName(String name) {
        var sql = """
                SELECT count(state_id)
                FROM states
                WHERE name=?
                """;
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,name);
        return count !=null &&count>0;
    }

    @Override
    public boolean existCountryWithCountryId(String country_id) {
        var sql = """
                SELECT count(country_id)
                FROM countries
                WHERE country_id=?
                """;
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,country_id);
        return count !=null &&count>0;
    }

    @Override
    public void updateState(State update) {
        if(update.getName()!=null){
            String sql = "UPDATE states SET name = ? WHERE state_id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getName(),
                    update.getState_id()
            );
            System.out.println("update state name result = " +result);
        }

        if (update.getCountry_id() != null) {
            // Check if the provided country_id exists in the countries table
            boolean countryExists = existCountryWithCountryId(update.getCountry_id());

            if (countryExists) {
                // Perform the update with the valid country_id
                String sql = "UPDATE states SET country_id = ? WHERE state_id = ?";
                int result = jdbcTemplate.update(
                        sql,
                        update.getCountry_id(),
                        update.getState_id()
                );
                System.out.println("update state country result = " + result);
            } else {
                // Handle the case where the country_id is not valid
                System.out.println("Invalid country_id: " + update.getCountry_id());
            }
        }
    }
}
