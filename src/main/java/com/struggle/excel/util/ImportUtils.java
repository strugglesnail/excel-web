package com.struggle.excel.util;

import org.apache.poi.ss.usermodel.*;

import java.util.*;

/**
 * @auther strugglesnail
 * @date 2020/10/19 20:58
 * @desc Excel文件导入
 */
public abstract class ImportUtils {

    //获取Excel数据集合

    // 获取单个sheet的数据
    private Map<Integer, List<Object>> getSingleSheetData(Sheet sheet, int rowIndex, int colIndex) {
        //存储行列表
        Map<Integer, List<Object>> cellsMap = new HashMap<>();
        for (int i = rowIndex; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            //获取每一行的单元格
            List<Object> cells = getCells(row, colIndex);
            cellsMap.put(i, cells);
        }
        return cellsMap;
    }

    // 存储每一行的单元格
    private List<Object> getCells(Row row, int colIndex) {
        List<Object> cells = new ArrayList<>();//每一列的单元格数据
        for (int j = colIndex; j < row.getLastCellNum(); j++) {
            if (row.getCell(j) != null){
                cells.add(this.getCellValue(row.getCell(j)));
            }
        }
        return cells;
    }

    // 获取单元格数据
    private Object getCellValue(Cell cell) {
        Object value = null;
        int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_NUMERIC:
                value = cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            default:
                break;
        }
        return value;
    }
}
