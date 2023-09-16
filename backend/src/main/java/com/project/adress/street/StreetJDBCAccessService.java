package com.project.adress.street;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("Streetjdbc")
public class StreetJDBCAccessService implements StreetDao {

    private final JdbcTemplate jdbcTemplate;
    private final StreetRowMapper streetRowMapper;

    public StreetJDBCAccessService(JdbcTemplate jdbcTemplate, StreetRowMapper streetRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.streetRowMapper = streetRowMapper;
    }

    @Override
    public List<Street> selectAllStreets() {
        var sql = """
                 SELECT city_id,name,street_id
                 FROM streets
                 """;
        return jdbcTemplate.query(sql, streetRowMapper);
    }

    @Override
    public Optional<Street> selectStreetById(Integer id) {
        var sql = """
                SELECT city_id,name,street_id
                FROM streets
                WHERE street_id=?
                """;
        return jdbcTemplate.query(sql, streetRowMapper,id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertStreet(Street street) {
        var sql = """
                INSERT INTO streets(street_id,name,city_id)
                VALUES(?,?,?)
                """;
        int result = jdbcTemplate.update(
                sql,
                street.getStreet_id(),
                street.getName(),
                street.getCity_id()
        );
        System.out.println("jdbcTemplate.update = "+result);

    }

    @Override
    public void deleteStreetById(Integer id) {
        var sql = """
                DELETE 
                FROM streets
                WHERE street_id = ?
                """;
        Integer result = jdbcTemplate.update(sql,id);
        System.out.println("deleteStreetById result = "+ result);

    }

    @Override
    public boolean existStreetById(Integer id) {
        var sql = """
                SELECT count(street_id)
                FROM streets
                WHERE street_id=?
                """;
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,id);
        return count !=null &&count>0;
    }

    @Override
    public boolean existStreetWithName(String name) {
        var sql = """
                SELECT count(street_id)
                FROM streets
                WHERE name=?
                """;
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,name);
        return count !=null &&count>0;
    }

    @Override
    public boolean existCityWithCityId(Integer city_id) {
        var sql = """
                SELECT count(city_id)
                FROM cities
                WHERE city_id=?
                """;
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,city_id);
        return count !=null &&count>0;
    }

    @Override
    public void updateStreet(Street update) {
        if(update.getName()!=null){
            String sql = "UPDATE streets SET name = ? WHERE street_id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getName(),
                    update.getStreet_id()
            );
            System.out.println("update street name result = " +result);
        }

        if (update.getCity_id() != null) {
            // Check if the provided city_id exists in the countries table
            boolean cityExists = existCityWithCityId(update.getCity_id());

            if (cityExists) {
                // Perform the update with the valid city_id
                String sql = "UPDATE streets SET city_id = ? WHERE street_id = ?";
                int result = jdbcTemplate.update(
                        sql,
                        update.getCity_id(),
                        update.getStreet_id()
                );
                System.out.println("update street city result = " + result);
            } else {
                // Handle the case where the city_id is not valid
                System.out.println("Invalid city_id: " + update.getCity_id());
            }
        }
    }
}
