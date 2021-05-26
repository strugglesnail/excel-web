package com.struggle.excel.model;

public class ElTableField {
    //主键Id
    private Long id;

    //表Id
    private Long tabId;

    //字段Ids(逗号分隔)
    private String fieldIds;

    /**
     * 获取主键Id
     *
     * @return id - 主键Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键Id
     *
     * @param id 主键Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取表Id
     *
     * @return tab_id - 表Id
     */
    public Long getTabId() {
        return tabId;
    }

    /**
     * 设置表Id
     *
     * @param tabId 表Id
     */
    public void setTabId(Long tabId) {
        this.tabId = tabId;
    }

    /**
     * 获取字段Ids(逗号分隔)
     *
     * @return field_ids - 字段Ids(逗号分隔)
     */
    public String getFieldIds() {
        return fieldIds;
    }

    /**
     * 设置字段Ids(逗号分隔)
     *
     * @param fieldIds 字段Ids(逗号分隔)
     */
    public void setFieldIds(String fieldIds) {
        this.fieldIds = fieldIds;
    }
}