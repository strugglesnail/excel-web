package com.struggle.excel.controller;

import com.struggle.excel.HandleCenter;
import com.struggle.excel.common.ServerResponse;
import com.struggle.excel.model.TableData;
import com.struggle.excel.model.TableFieldData;
import com.struggle.excel.model.TableNode;
import com.struggle.excel.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ExcelService excelService;

    @GetMapping("/getTableList")
    public ServerResponse getTableList(@RequestParam(defaultValue = "excel", required = false) String baseName) {
        HandleCenter center = new HandleCenter();
        List<TableNode> tableList = center.getTableList(baseName);
        return ServerResponse.createBySuccess(tableList);
    }

    @GetMapping("/getFields")
    public ServerResponse getColumnList() {
        List<TableFieldData> fields = excelService.getFields();
        return ServerResponse.createBySuccess(fields);
    }

    @PostMapping("/addFields")
    public ServerResponse getColumnList(@RequestBody TableData tableData) {
        excelService.addFields(tableData);
        return ServerResponse.createBySuccess();
    }
}
