package com.struggle.excel;

import com.struggle.excel.model.TableData;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther strugglesnail
 * @date 2021/5/25 22:33
 * @desc
 */
public class HandleCenter {

    private Statement stmt = null;

    private ResultSet rs = null;

    private Connection conn = null;

    public HandleCenter(){
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

    public void getRes() {
        try {
            stmt = conn.prepareStatement("select * from el_field");
            rs = ((PreparedStatement) stmt).executeQuery();
            while (rs.next()) {
                System.out.println(rs.getLong(1));
                System.out.println(rs.getInt(2));
                System.out.println(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.close(conn, stmt, rs);
        }
    }

    public void close(Connection conn , Statement stmt, ResultSet rs){
        try{
            if(rs != null){
                rs.close();
                rs = null ;
            }
            if(stmt != null){
                stmt.close();
                stmt = null ;
            }
            if(conn != null){
                conn.close();
                conn = null;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void demo() {
        Map<String, TableData> map = new HashMap<>();
        map.put("sheet1", new TableData());
        map.put("sheet2", new TableData());

        map.forEach((key, value) -> {
            // 1.根据key获取sheet表格数据
            // 2.根据value拼接insert语句
            // 3.根据字段类型，字段值
        });
    }

    public static void main(String[] args) {
        HandleCenter oracleDao = new HandleCenter();
        oracleDao.getRes();
    }
}
