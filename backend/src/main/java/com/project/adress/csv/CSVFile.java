package com.project.adress.csv;

import java.util.List;

public class CSVFile {
    private String name;
    private List<String> line;
    private List<List<String>> content;
    private String dbTable;

    public void setContent(List<List<String>> content) {
        this.content = content;
    }

    public String getDbTable() {
        return dbTable;
    }

    public void setDbTable(String dbTable) {
        this.dbTable = dbTable;
    }

    CSVFile(String name){
        this.name=name;
    }
    CSVFile(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLine() {
        return line;
    }

    public void setLine(List<String> line) {
        this.line = line;
    }

    public List<List<String>> getContent() {
        return content;
    }

    public void setContent() {
        this.content = content;
    }
    public void addContent(List<String> list){
        this.content.add(list);
    }
    public void clearContent(){
        this.content.clear();
    }
}
