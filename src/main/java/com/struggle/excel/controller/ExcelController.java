package com.struggle.excel.controller;

import com.struggle.excel.HandleCenter;
import com.struggle.excel.common.ServerResponse;
import com.struggle.excel.model.TableData;
import com.struggle.excel.model.TableNode;
import org.springframework.web.bind.annotation.*;

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
    public List<TableNode> getTableList(@RequestParam(defaultValue = "excel", required = false) String baseName) {
        HandleCenter center = new HandleCenter();
        List<TableNode> tableList = center.getTableList(baseName);
        return tableList;
    }

    @PostMapping("/addFields")
    public ServerResponse getColumnList(@RequestBody TableData tableData) {
//        HandleCenter center = new HandleCenter();
//        List<Map<String, String>> tableList = center.getColumnList(baseName, tableName);
        return ServerResponse.createBySuccess();
    }
}
