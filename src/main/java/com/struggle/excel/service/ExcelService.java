package com.struggle.excel.service;

import com.struggle.excel.mapper.ElFieldMapper;
import com.struggle.excel.mapper.ElTableFieldMapper;
import com.struggle.excel.mapper.ElTableMapper;
import com.struggle.excel.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @auther strugglesnail
 * @date 2021/5/30 23:33
 * @desc
 */
@Service
public class ExcelService {

    @Autowired
    private ElTableMapper tableMapper;
    @Autowired
    private ElFieldMapper fieldMapper;
    @Autowired
    private ElTableFieldMapper tableFieldMapper;

    @Transactional
    public void addFields(List<TableData> dataList) {
        for (TableData tableData : dataList) {
            this.addFields(tableData);
        }
    }

    // 保存表信息
    public void addFields(TableData data) {
        // 保存表信息
        ElTable table = new ElTable();
        table.setName(data.getTableName());
        ElTable existTab = tableMapper.getOne(table);
        if (existTab == null) {
            tableMapper.save(table);
        }

        // 保存字段信息
        fieldMapper.saveBatch(data.getFields());

        // 保存表与字段关系信息
        String fieldIds = data.getFields().stream().map(f -> String.valueOf(f.getId())).collect(Collectors.joining(","));
        ElTableField tf = new ElTableField();
        tf.setTabId(Objects.isNull(table.getId()) ? existTab.getId(): table.getId());
        tf.setFieldIds(fieldIds);
        tableFieldMapper.save(tf);
    }

    public List<TableFieldData> getTableFields() {
        List<ElTableField> list = tableFieldMapper.list(new ElTableField());
        List<TableFieldData> tableFieldDataList = new ArrayList<>();
        for (ElTableField tableField : list) {
            if(Objects.nonNull(tableField.getTabId())) {
                ElTable table = tableMapper.getById(tableField.getTabId());
                List<ElField> fields = fieldMapper.getFields(tableField.getFieldIds());
                TableFieldData tableFieldData = new TableFieldData();
                tableFieldData.setTableFieldId(tableField.getId());
                tableFieldData.setTableName(table.getName());
                tableFieldData.setFieldNames(fields.stream().map(f -> f.getName() + "(" + f.getType() + ")").collect(Collectors.joining(",")));
                tableFieldData.setFieldList(fields);
                tableFieldDataList.add(tableFieldData);
            }
        }
        return tableFieldDataList;
    }

    // 删除表与字段的关联信息
    @Transactional
    public void deleteTableField(Long tableFieldId) {
        ElTableField tableField = tableFieldMapper.getById(tableFieldId);
        tableFieldMapper.delete(tableFieldId);
        int tabIdCount = tableFieldMapper.getTabIdCount(tableField.getTabId());
        if (tabIdCount <= 1) {
            tableMapper.delete(tableField.getTabId());
        }
        fieldMapper.deleteFields(tableField.getFieldIds());
    }
}
