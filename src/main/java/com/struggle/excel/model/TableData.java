package com.struggle.excel.model;

import java.util.List;

/**
 * @author strugglesnail
 * @date 2021/5/26
 * @desc 表对应关系
 */
public class TableData {
    private Long id;
    private String tableName;
    List<ElField> fields;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ElField> getFields() {
        return fields;
    }

    public void setFields(List<ElField> fields) {
        this.fields = fields;
    }
}
