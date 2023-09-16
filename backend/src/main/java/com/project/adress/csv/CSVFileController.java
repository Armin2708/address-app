package com.project.adress.csv;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/csv-file")
public class CSVFileController {
    private final CSVFileService csvFileService;

    public CSVFileController(CSVFileService csvFileService) {
        this.csvFileService = csvFileService;
    }

    @PostMapping("{CSVFile_name}")
    public void getCSVFileByName(
            @PathVariable("CSVFile_name") String CSVFile_name,
            @RequestParam("dataTable_name") String dataTable_name
    ) throws IOException {
        System.out.println(CSVFile_name+" "+dataTable_name);
        csvFileService.insertCSVFile(dataTable_name,CSVFile_name);
        System.out.println("Controller layer passed");
    }
}
