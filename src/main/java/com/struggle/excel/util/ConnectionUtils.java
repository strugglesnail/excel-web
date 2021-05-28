package com.struggle.excel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author strugglesnail
 * @date 2021/5/28
 * @desc
 */
public class ConnectionUtils {

    // 获取连接
    public static Connection getConnection(){
        try{
//            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
//            String url="jdbc:oracle:thin:@10.11.0.31:1521:orcl"; //orcl为数据库的SID
//            String user="meeting";
//            String password="meeting";
            String driver = "com.mysql.jdbc.Driver";    //驱动程序
            String url = "jdbc:mysql://localhost:3306/excel?useSSL=false";    //连接的URL,db_name为数据库名
            String username = "root";    //用户名
            String password = "123456";    //密码
            Class.forName(driver).newInstance();
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection conn , Statement stmt, ResultSet rs){
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
}
