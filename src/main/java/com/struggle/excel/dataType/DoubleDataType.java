package com.struggle.excel.dataType;


import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author strugglesnail
 * @date 2021/5/27
 * @desc
 */
public class DoubleDataType implements DataType<Double> {

    @Override
    public String[] type() {
        return new String[] { "double", "number" };
    }



    @Override
    public void setDataType(PreparedStatement statement, int index, Double val) throws SQLException {
        statement.setDouble(index, val);
    }
}
