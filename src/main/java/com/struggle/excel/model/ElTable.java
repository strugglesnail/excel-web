package com.struggle.excel.model;

public class ElTable {
    //主键Id
    private Long id;

    //表名称
    private String name;

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
     * 获取表名称
     *
     * @return name - 表名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置表名称
     *
     * @param name 表名称
     */
    public void setName(String name) {
        this.name = name;
    }
}