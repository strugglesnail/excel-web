package com.struggle.excel.dataType;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

/**
 * @author strugglesnail
 * @date 2021/5/27
 * @desc
 */
public class DateDataType implements DataType<Date> {

    @Override
    public String[] type() {
        return new String[] { "date", "timestamp" };
    }



    @Override
    public void setDataType(PreparedStatement statement, int index, Date val) throws SQLException {
        statement.setDate(index, val);
    }
}
