package com.project.adress.csv;

import java.util.List;

public interface CSVFileDAO {
    public void batchCountry( String[] list);
    public void batchState( String[] list);
    public void batchCounty( String[] list);
    public void batchStreet( String[] list);
    public void batchCity( String[] list);
    public void batchNumber( String[] list);
}
