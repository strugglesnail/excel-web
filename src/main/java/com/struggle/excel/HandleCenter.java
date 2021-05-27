package com.struggle.excel;

import com.struggle.excel.model.ElField;
import com.struggle.excel.model.TableData;
import com.struggle.excel.util.ImportUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther strugglesnail
 * @date 2021/5/25 22:33
 * @desc
 */
public class HandleCenter {

    private Connection conn = null;

    private Workbook workbook;

    public HandleCenter(InputStream stream) {
        try {
            workbook = WorkbookFactory.create(stream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        this.getConnection();
    }

    public void getConnection(){
        try{
//            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
//            String url="jdbc:oracle:thin:@10.11.0.31:1521:orcl"; //orcl为数据库的SID
//            String user="meeting";
//            String password="meeting";
            String Driver="com.mysql.jdbc.Driver";    //驱动程序
            String url="jdbc:mysql://localhost:3306/excel?useSSL=false";    //连接的URL,db_name为数据库名
            String username="root";    //用户名
            String password="123456";    //密码
            Class.forName(Driver).newInstance();
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getTableList(String baseName) {
        List<String> tableList = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT table_name FROM information_schema.tables WHERE table_schema = ? AND table_type='base table'");
            ps.setString(1, baseName);
            rs = ps.executeQuery();
            while (rs.next()) {
                String string = rs.getString(1);
                System.out.println(string);
                tableList.add(string);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.close(conn, ps, rs);
        return tableList;
    }

    public void close(Connection conn , Statement stmt, ResultSet rs){
        try{
            if(stmt != null){
                stmt.close();
                stmt = null ;
            }
            if(conn != null){
                conn.close();
                conn = null;
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    private void demo(Map<String, TableData> map) throws Exception {
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
                    switch (field.getType()) {
                        case "1":
                        statement.setLong(i + 1, (Long) svalue.get(field.getIndex() - 1));
                        break;
                        case "2":
                        statement.setDouble(i + 1, (Double) svalue.get(field.getIndex() - 1));
                        break;
                        case "3":
                        statement.setString(i + 1, (String) svalue.get(field.getIndex() - 1));
                        break;
                        case "4":
                        statement.setDate(i + 1, (Date) svalue.get(field.getIndex() - 1));
                        break;
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
        this.close(conn, statement, null);
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
        System.out.println(center.getTableList("excel"));
    }
}
