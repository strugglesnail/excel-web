package com.struggle.excel.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;

import java.util.*;

/**
 * @auther strugglesnail
 * @date 2020/10/19 20:58
 * @desc Excel文件导入
 */
public class ImportUtils {

    //获取Excel数据集合

    // 获取单个sheet的数据
    public static Map<Integer, List<Object>> getSingleSheetData(Sheet sheet, int rowIndex, int colIndex) {
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
    private static List<Object> getCells(Row row, int colIndex) {
        List<Object> cells = new ArrayList<>();//每一列的单元格数据
        for (int j = colIndex; j < row.getLastCellNum(); j++) {
            if (row.getCell(j) != null){
                cells.add(getCellValue(row.getCell(j)));
            }
        }
        return cells;
    }

    // 获取单元格数据
    private static Object getCellValue(Cell cell) {
        Object value = null;
        int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_NUMERIC:
                double numericCellValue = cell.getNumericCellValue();
                short format = cell.getCellStyle().getDataFormat();
                /*判断日期个格式是否是 2021/01/01 这样
                 * 14 yyyy-MM-dd / 2017/01/01
                 * 31 yyyy年m月d日
                 * */
                if(format == 14 || format == 31){
                    Date javaDate = HSSFDateUtil.getJavaDate(numericCellValue);
                    if (Objects.nonNull(javaDate)) {
                        value = new java.sql.Date(javaDate.getTime());
                    }
                } else {
                   value = numericCellValue;
                }
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
