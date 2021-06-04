package com.struggle.excel.dataType;


import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author strugglesnail
 * @date 2021/5/27
 * @desc
 */
public class IntegerDataType implements DataType<Integer> {

    @Override
    public String[] type() {
        return new String[] { "int1" };
    }



    @Override
    public void setDataType(PreparedStatement statement, int index, Integer val) throws SQLException {
        statement.setInt(index, val);
    }
}
