package com.struggle.excel.dataType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author strugglesnail
 * @date 2021/5/27
 * @desc 数据类型
 */
public interface DataType<T> {

    // 类型
    String type();

    void setDataType(PreparedStatement statement, int index, T t)  throws SQLException;
}
