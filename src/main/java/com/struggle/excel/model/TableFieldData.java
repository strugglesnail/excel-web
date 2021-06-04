package com.struggle.excel.model;

import java.util.List;

/**
 * @auther strugglesnail
 * @date 2021/5/31 22:33
 * @desc
 */
public class TableFieldData {

    private Long tableFieldId;

    private String tableName;

    private String fieldNames;

    private List<ElField> fieldList;

    public Long getTableFieldId() {
        return tableFieldId;
    }

    public void setTableFieldId(Long tableFieldId) {
        this.tableFieldId = tableFieldId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(String fieldNames) {
        this.fieldNames = fieldNames;
    }

    public List<ElField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<ElField> fieldList) {
        this.fieldList = fieldList;
    }
}
