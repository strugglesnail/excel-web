package com.struggle.excel;

import com.struggle.excel.dataType.*;
import com.struggle.excel.model.ElField;
import com.struggle.excel.model.TableData;
import com.struggle.excel.model.TableNode;
import com.struggle.excel.util.ConnectionUtils;
import com.struggle.excel.util.ImportUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther strugglesnail
 * @date 2021/5/25 22:33
 * @desc
 */
public class HandleCenter {

    private Connection conn;

    private Workbook workbook;

    List<DataType> dataTypeList = new ArrayList<>();

    public HandleCenter() {
        // 获取连接
        conn = ConnectionUtils.getConnection();
    }

    public HandleCenter(InputStream stream) {
        try {
            workbook = WorkbookFactory.create(stream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        // 获取连接
        conn = ConnectionUtils.getConnection();
        // 初始化数据类型
        dataTypeList.add(new LongDataType());
        dataTypeList.add(new DoubleDataType());
        dataTypeList.add(new StringDataType());
        dataTypeList.add(new DateDataType());
    }


    // 获取指定库的表名称
    public List<TableNode> getTableList(String baseName) {
        List<TableNode> tableList = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = conn.prepareStatement("SELECT table_name FROM information_schema.tables WHERE table_schema = ? AND table_type='base table'");
            ps.setString(1, baseName);
            rs = ps.executeQuery();
            while (rs.next()) {
                TableNode node = new TableNode();
                String tableName = rs.getString(1);
                List<TableNode> columnList = getColumnList(baseName, tableName, rs.getRow());
                node.setId(rs.getRow() + "");
                node.setLabel(tableName);
                node.setChildren(columnList);
                tableList.add(node);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        ConnectionUtils.close(conn, ps, rs);
        return tableList;
    }

    // 获取指定库的表名称
    public List<TableNode> getColumnList(String baseName, String tableName, int row) {
        List<TableNode> tableList = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = conn.prepareStatement("SELECT column_name, data_type FROM information_schema.columns WHERE table_schema=? AND table_name=?");
            ps.setString(1, baseName);
            ps.setString(2, tableName);
            rs = ps.executeQuery();
            while (rs.next()) {
                TableNode node = new TableNode();
                String columnName = rs.getString(1);
                String dataType = rs.getString(2);
                node.setId(row + "-" + rs.getRow());
                node.setLabel(columnName);
                tableList.add(node);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        ConnectionUtils.close(conn, ps, rs);
        return tableList;
    }




    private void core(Map<String, TableData> map) throws Exception {
        // 线程
        StringBuilder builder = new StringBuilder();
        PreparedStatement statement = null;
        conn.setAutoCommit(false);
        // 遍历每一个sheet
        for (Map.Entry<String, TableData> entry : map.entrySet()) {
            // sheet名称
            String key = entry.getKey();
            // sheet数据
            TableData value = entry.getValue();


            // 1.根据value拼接insert语句
            String fields = "", placeholders = "";
            List<ElField> fieldList = null;
            if (CollectionUtils.isNotEmpty(value.getFields())) {
                fieldList = sortFields(value.getFields());
                fields = fieldList.stream().map(f -> f.getName()).collect(Collectors.joining(","));
                placeholders = value.getFields().stream().map(f -> "?").collect(Collectors.joining(","));
            }

            builder.append("INSERT INTO ").append(value.getName()).append("(").append(fields).append(") ")
                    .append("values (").append(placeholders).append(")");

            String sql = builder.toString();
            System.out.println(sql);
            statement = conn.prepareStatement(sql);

            // 2.根据key获取sheet表格数据
            Sheet sheet = workbook.getSheet(key);
            Map<Integer, List<Object>> sheetData = ImportUtils.getSingleSheetData(sheet, 1, 0);

            // 3.根据字段类型，字段值设置setInt,setString
            for (Map.Entry<Integer, List<Object>> e : sheetData.entrySet()) {
                Integer row = e.getKey();
                // 每行的单元格
                List<Object> svalue = e.getValue();
                System.out.println(svalue);
                System.out.println(fieldList.stream().map(d -> d.getName()).collect(Collectors.joining(",")));

                // 遍历每一行，填充字段值
                for (int i = 0; i < fieldList.size(); i++) {
                    ElField field = fieldList.get(i);
                    for (DataType dataType : dataTypeList) {
                        boolean anyMatch = Arrays.stream(dataType.type()).anyMatch(t -> t.equals(field.getType()));
                        if (anyMatch) {
                            dataType.setDataType(statement, i+ 1, svalue.get(field.getIndex() - 1));
                            break;
                        }
                    }
                }
                statement.addBatch();
                // 每隔50条提交一次
                if (row != 0 && row % 50 == 0) {
                    statement.executeBatch();
                    conn.commit();
                    statement.clearBatch();
                }
            }
            statement.executeBatch();
            conn.commit();
            statement.clearBatch();
        }
        // 关闭连接
        ConnectionUtils.close(conn, statement, null);
    }

    // 字段排序
    private List<ElField> sortFields(List<ElField> fields) {
        return fields.stream().sorted(Comparator.comparingInt(ElField::getIndex)).collect(Collectors.toList());
    }

    public static void main(String[] args) throws Exception {
        Map<String, TableData> map = new HashMap<>();
        TableData tableData = new TableData();
        List<ElField> fields0 = new ArrayList<>();
        tableData.setName("person");
        fields0.add(new ElField(1L, 1, "name", ""));
        fields0.add(new ElField(2L, 2, "age", ""));
        fields0.add(new ElField(3L, 3, "sex", ""));
        tableData.setFields(fields0);
//        TableData tableData2 = new TableData();
//        List<ElField> fields2 = new ArrayList<>();
//        fields2.add(new ElField(1L, 1, "id", 1));
//        fields2.add(new ElField(2L, 2, "index", 1));
//        fields2.add(new ElField(3L, 3, "name", 2));
//        fields2.add(new ElField(4L, 4, "type", 1));
//        tableData2.setName("person");
//        tableData2.setFields(fields2);
        map.put("sheet1", tableData);
//        map.put("sheet2", tableData2);
        InputStream stream = new FileInputStream(new File("C:\\Users\\user\\Desktop\\excel-web.xlsx"));
        HandleCenter center = new HandleCenter(stream);
//        center.demo(map);
//        System.out.println(center.getTableList("excel"));
//        System.out.println(center.getColumnList("excel" , "person"));
    }
}
