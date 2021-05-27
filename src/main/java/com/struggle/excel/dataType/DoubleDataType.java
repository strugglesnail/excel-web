package com.struggle.excel.dataType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author strugglesnail
 * @date 2021/5/27
 * @desc
 */
public class DoubleDataType implements DataType<Long> {
    @Override
    public String type() {
        return "bigint";
    }

    @Override
    public void setDataType(PreparedStatement statement, int index, Long aLong) throws SQLException {
        statement.setLong(index, aLong);
    }
}
