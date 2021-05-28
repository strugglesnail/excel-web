package com.struggle.excel.dataType;


import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author strugglesnail
 * @date 2021/5/27
 * @desc
 */
public class StringDataType implements DataType<String> {

    @Override
    public String[] type() {
        return new String[] { "varchar", "varchar2" };
    }



    @Override
    public void setDataType(PreparedStatement statement, int index, String val) throws SQLException {
        statement.setString(index, val);
    }
}
