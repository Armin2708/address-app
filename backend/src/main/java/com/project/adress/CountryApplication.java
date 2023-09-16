package com.project.adress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;

@SpringBootApplication
public class CountryApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(CountryApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			insertCSVData("/Users/arminrad/Desktop/adress start/backend/src/main/java/com/project/adress/csv/batchFiles/Pays.csv");
		}
		catch (Exception e){
			System.out.println(e);
		}
	}
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private void insertCSVData(String fileName) throws IOException {
		System.out.println("Service layer started");
		BufferedReader csvReader = new BufferedReader(new FileReader(fileName));

		try {
			String rawLine;
			while ((rawLine = csvReader.readLine()) != null) {
				System.out.println(rawLine);
				String[] splitLine = rawLine.split(",");
				System.out.println("CSV file converted");

				var sql = "INSERT INTO countries (country_id, name) VALUES (?, ?)";

					jdbcTemplate.update(
							sql,
							splitLine[0].trim(),  // Assuming the first value is for country_id
							splitLine[1].trim()   // Assuming the second value is for name
					);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			csvReader.close();
		}
	}
}
