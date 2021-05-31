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

    // 保存表信息
    @Transactional
    public void addFields(TableData data) {
        // 保存表信息
        ElTable table = new ElTable();
        table.setName(data.getName());
        tableMapper.save(table);

        // 保存字段信息
        fieldMapper.saveBatch(data.getFields());

        // 保存表与字段关系信息
        String fieldIds = data.getFields().stream().map(f -> String.valueOf(f.getId())).collect(Collectors.joining(","));
        ElTableField tf = new ElTableField();
        tf.setTabId(table.getId());
        tf.setFieldIds(fieldIds);
        tableFieldMapper.save(tf);
    }

    public List<TableFieldData> getFields() {
        List<ElTableField> list = tableFieldMapper.list(new ElTableField());
        List<TableFieldData> tableFieldDataList = new ArrayList<>();
        for (ElTableField tableField : list) {
            System.out.println(tableField.getId());
            ElTable table = tableMapper.getById(tableField.getId());
            List<ElField> fields = fieldMapper.getFields(tableField.getFieldIds());
            TableFieldData tableFieldData = new TableFieldData();
            tableFieldData.setTableName(table.getName());
            tableFieldData.setFieldNames(fields.stream().map(f -> f.getName() + "(" + f.getType() +")").collect(Collectors.joining(",")));
            tableFieldDataList.add(tableFieldData);
        }
        return tableFieldDataList;
    }
}
