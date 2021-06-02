package com.struggle.excel.model;

/**
 * @auther strugglesnail
 * @date 2021/6/2 23:11
 * @desc
 */
public class ExcelData {

    private String sheetName;

    private TableData tableData;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public TableData getTableData() {
        return tableData;
    }

    public void setTableData(TableData tableData) {
        this.tableData = tableData;
    }
}
