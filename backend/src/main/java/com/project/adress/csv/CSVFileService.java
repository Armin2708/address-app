package com.project.adress.csv;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class CSVFileService {

    public static CSVFileDAO csvFileDAO = null;

    public CSVFileService(@Qualifier("csvJdbc")CSVFileDAO csvFileDAO) {
        this.csvFileDAO = csvFileDAO;
    }

    void insertCSVFile(String tableName, String fileName) throws IOException {

        BufferedReader csvReader = new BufferedReader(new FileReader(fileName));

        try {
            String rawLine;
            while ((rawLine = csvReader.readLine()) != null) {
                System.out.println(rawLine);
                String[] splitLine = rawLine.split(",");

                if (tableName=="countries"){
                    csvFileDAO.batchCountry(splitLine);
                }
                if (tableName=="states"){
                    csvFileDAO.batchState(splitLine);
                }
                if (tableName=="counties"){
                    csvFileDAO.batchCounty(splitLine);
                }
                if (tableName=="cities"){

                }
                if (tableName=="streets"){

                }
                if (tableName=="numbers"){

                }
                else {

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            csvReader.close();
        }
    }
}
