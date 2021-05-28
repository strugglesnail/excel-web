package com.struggle.excel.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @auther strugglesnail
 * @date 2021/5/28 22:02
 * @desc
 */
public class TableNode {

    private String label;
    private String id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TableNode> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TableNode> getChildren() {
        return children;
    }

    public void setChildren(List<TableNode> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
