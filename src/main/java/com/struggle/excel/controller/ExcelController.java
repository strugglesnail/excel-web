package com.struggle.excel.controller;

import com.struggle.excel.HandleCenter;
import com.struggle.excel.common.ServerResponse;
import com.struggle.excel.model.TableData;
import com.struggle.excel.model.TableFieldData;
import com.struggle.excel.model.TableNode;
import com.struggle.excel.service.ExcelService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    @PostMapping("/upload")
    public ServerResponse upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            return ServerResponse.createByErrorMessage("文件不能为空");
        }

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        int numberOfSheets = workbook.getNumberOfSheets();
        List<String> sheetNameList = new ArrayList<>(numberOfSheets);
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheetAt = workbook.getSheetAt(i);
            sheetNameList.add(sheetAt.getSheetName());
        }
        return ServerResponse.createBySuccess(sheetNameList);
    }

    public static void main(String[] args) throws Exception {
        InputStream stream = new FileInputStream(new File("C:\\Users\\user\\Desktop\\excel-web.xlsx"));

        Workbook workbook = WorkbookFactory.create(stream);

        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheetAt = workbook.getSheetAt(i);
            System.out.println(sheetAt.getSheetName());
        }
    }
}
