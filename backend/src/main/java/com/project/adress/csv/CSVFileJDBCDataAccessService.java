package com.project.adress.csv;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("csvJdbc")
public class CSVFileJDBCDataAccessService implements CSVFileDAO{

    private static JdbcTemplate jdbcTemplate = null;

    public CSVFileJDBCDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void batchCountry(String[] splitLine) {
        var sql = "INSERT INTO countries (country_id, name) VALUES (?, ?)";

        int result =jdbcTemplate.update(
                sql,
                splitLine[0].trim(),
                splitLine[1].trim()
        );
            System.out.println("jdbcTemplate.update = " + result);
        }

    @Override
    public void batchState(String[] splitLine) {
        var sql = "INSERT INTO states (state_id, name,country_id) VALUES (?, ?,?)";

        int result =jdbcTemplate.update(
                sql,
                splitLine[0].trim(),
                splitLine[1].trim(),
                splitLine[2].trim()
        );
        System.out.println("jdbcTemplate.update = " + result);
    }

    @Override
    public void batchCounty(String[] splitLine) {
        var sql = "INSERT INTO counties (county_id, name,capital,state_id) VALUES (?, ?,?,?)";

        int result =jdbcTemplate.update(
                sql,
                splitLine[0].trim(),
                splitLine[1].trim(),
                splitLine[2].trim(),
                splitLine[3].trim()
        );
        System.out.println("jdbcTemplate.update = " + result);
    }

    @Override
    public void batchStreet(String[] splitLine) {
        var sql = "INSERT INTO countries (country_id, name) VALUES (?, ?)";

        int result =jdbcTemplate.update(
                sql,
                splitLine[0].trim(),  // Assuming the first value is for country_id
                splitLine[1].trim()   // Assuming the second value is for name
        );
        System.out.println("jdbcTemplate.update = " + result);
    }

    @Override
    public void batchCity(String[] splitLine) {
        var sql = "INSERT INTO countries (country_id, name) VALUES (?, ?)";

        int result =jdbcTemplate.update(
                sql,
                splitLine[0].trim(),  // Assuming the first value is for country_id
                splitLine[1].trim()   // Assuming the second value is for name
        );
        System.out.println("jdbcTemplate.update = " + result);
    }

    @Override
    public void batchNumber(String[] splitLine) {
        var sql = "INSERT INTO countries (country_id, name) VALUES (?, ?)";

        int result =jdbcTemplate.update(
                sql,
                splitLine[0].trim(),  // Assuming the first value is for country_id
                splitLine[1].trim()   // Assuming the second value is for name
        );
        System.out.println("jdbcTemplate.update = " + result);
    }
}
