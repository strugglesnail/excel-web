package com.struggle.excel.service;

import com.struggle.excel.mapper.ElFieldMapper;
import com.struggle.excel.mapper.ElTableFieldMapper;
import com.struggle.excel.mapper.ElTableMapper;
import com.struggle.excel.model.ElTable;
import com.struggle.excel.model.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addFields(TableData data) {
        // 保存表信息
        ElTable table = new ElTable();
        table.setName(data.getName());
        tableMapper.save(table);

        // 保存字段信息
        fieldMapper.saveBatch(data.getFields());

        // 保存表与字段关系信息
        tableFieldMapper

    }
}
