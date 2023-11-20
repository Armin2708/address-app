package com.project.adress.property;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("Propertyjdbc")
public class PropertyJDBCAccessService implements PropertyDao {

    private final JdbcTemplate jdbcTemplate;
    private final PropertyRowMapper propertyRowMapper;

    public PropertyJDBCAccessService(JdbcTemplate jdbcTemplate, PropertyRowMapper propertyRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.propertyRowMapper = propertyRowMapper;
    }

    @Override
    public List<Property> selectAllProperties(Integer streetId) {
        var sql = """
                 SELECT street_id,name,property_id
                 FROM properties
                 WHERE street_id=?
                 """;
        return jdbcTemplate.query(sql, propertyRowMapper,streetId);
    }

    @Override
    public Optional<Property> selectPropertyById(Integer id) {
        var sql = """
                SELECT street_id,name,property_id
                FROM properties
                WHERE property_id=?
                """;
        return jdbcTemplate.query(sql, propertyRowMapper,id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertProperty(Property property) {
        var sql = """
                INSERT INTO properties(property_id,name,street_id)
                VALUES(?,?,?)
                """;
        int result = jdbcTemplate.update(
                sql,
                property.getProperty_id(),
                property.getName(),
                property.getStreet_id()
        );
        System.out.println("jdbcTemplate.update = "+result);

    }

    @Override
    public void deletePropertyById(Integer id) {
        var sql = """
                DELETE 
                FROM properties
                WHERE property_id = ?
                """;
        Integer result = jdbcTemplate.update(sql,id);
        System.out.println("deletePropertyById result = "+ result);

    }

    @Override
    public boolean existPropertyById(Integer id) {
        var sql = """
                SELECT count(property_id)
                FROM properties
                WHERE property_id=?
                """;
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,id);
        return count !=null &&count>0;
    }

    @Override
    public boolean existPropertyWithName(String name) {
        var sql = """
                SELECT count(property_id)
                FROM properties
                WHERE name=?
                """;
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,name);
        return count !=null &&count>0;
    }

    @Override
    public boolean existStreetWithStreetId(Integer street_id) {
        var sql = """
                SELECT count(street_id)
                FROM streets
                WHERE street_id=?
                """;
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,street_id);
        return count !=null &&count>0;
    }

    @Override
    public void updateProperty(Property update) {
        if(update.getName()!=null){
            String sql = "UPDATE properties SET name = ? WHERE property_id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getName(),
                    update.getProperty_id()
            );
            System.out.println("update property name result = " +result);
        }

        if (update.getStreet_id() != null) {
            // Check if the provided street_id exists in the countries table
            boolean streetExists = existStreetWithStreetId(update.getStreet_id());

            if (streetExists) {
                // Perform the update with the valid street_id
                String sql = "UPDATE properties SET street_id = ? WHERE property_id = ?";
                int result = jdbcTemplate.update(
                        sql,
                        update.getStreet_id(),
                        update.getProperty_id()
                );
                System.out.println("update property street result = " + result);
            } else {
                // Handle the case where the street_id is not valid
                System.out.println("Invalid street_id: " + update.getStreet_id());
            }
        }
    }
}
