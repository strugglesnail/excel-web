package com.struggle.excel.model;

public class ElField {
    //主键Id
    private Long id;

    //字段序号
    private Integer index;

    //字段名称
    private String name;

    //字段类型
    private String type;

    public ElField() {
    }

    public ElField(Long id, Integer index, String name, String type) {
        this.id = id;
        this.index = index;
        this.name = name;
        this.type = type;
    }

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
     * 获取字段序号
     *
     * @return index - 字段序号
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * 设置字段序号
     *
     * @param index 字段序号
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * 获取字段名称
     *
     * @return name - 字段名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置字段名称
     *
     * @param name 字段名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取字段类型
     *
     * @return type - 字段类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置字段类型
     *
     * @param type 字段类型
     */
    public void setType(String type) {
        this.type = type;
    }
}