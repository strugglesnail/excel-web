package com.struggle.excel.controller;

import com.struggle.excel.HandleCenter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author strugglesnail
 * @date 2021/5/28
 * @desc
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @GetMapping("/getTableList")
    public List<String> getTableList(@RequestParam(defaultValue = "excel", required = false) String baseName) {
        HandleCenter center = new HandleCenter();
        List<String> tableList = center.getTableList(baseName);
        return tableList;
    }

    @GetMapping("/getColumnList")
    public Object getColumnList(@RequestParam(defaultValue = "excel", required = false) String baseName, @RequestParam(defaultValue = "person", required = false) String tableName) {
        HandleCenter center = new HandleCenter();
        List<Map<String, String>> tableList = center.getColumnList(baseName, tableName);
        return tableList;
    }
}
