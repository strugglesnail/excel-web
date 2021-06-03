package com.struggle.excel.controller;

import com.struggle.excel.HandleCenter;
import com.struggle.excel.common.ServerResponse;
import com.struggle.excel.model.ExcelData;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author strugglesnail
 * @date 2021/5/28
 * @desc
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    private static Map<String, Workbook> cacheWorkbook = new ConcurrentHashMap<>();

    private static final String WORK_BOOK_KEY = "workbook";

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
    public ServerResponse getColumnList(@RequestBody List<TableData> tableDatas) {
        excelService.addFields(tableDatas);
        return ServerResponse.createBySuccess();
    }
    @PostMapping("/upload")
    public ServerResponse upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            return ServerResponse.createByErrorMessage("文件不能为空");
        }

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        cacheWorkbook.put(WORK_BOOK_KEY, workbook);
        int numberOfSheets = workbook.getNumberOfSheets();
        List<String> sheetNameList = new ArrayList<>(numberOfSheets);
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheetAt = workbook.getSheetAt(i);
            sheetNameList.add(sheetAt.getSheetName());
        }
        return ServerResponse.createBySuccess(sheetNameList);
    }
    @PostMapping("/addExcelData")
    public ServerResponse addExcelData(@RequestBody List<ExcelData> excelData) throws Exception {

        if (!cacheWorkbook.containsKey(WORK_BOOK_KEY)) {
            return ServerResponse.createByErrorMessage("找不到Excel源文件");
        }
        Map<String, TableData> sheetMap = new HashMap<>();
        for (ExcelData excelDatum : excelData) {
            sheetMap.put(excelDatum.getSheetName(), excelDatum.getTableData());
        }
        Workbook workbook = cacheWorkbook.get(WORK_BOOK_KEY);
        HandleCenter center = new HandleCenter(workbook);
        center.core(sheetMap);
        return ServerResponse.createBySuccess(excelData);
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
