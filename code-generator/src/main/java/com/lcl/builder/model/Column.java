package com.lcl.builder.model;

import lombok.Data;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/12/116:38
 */
@Data
public class Column {
    private String columnName;//列名称
    private String columnName2;//驼峰命名法的列名称
    private String columnType;//列类型
    private String columnDbType;//列数据类型
    private String columnKey;//是否是主键

    @Override
    public String toString() {
        return "Column{" +
                "columnName='" + columnName + '\'' +
                ", columnName2='" + columnName2 + '\'' +
                ", columnType='" + columnType + '\'' +
                ", columnDbType='" + columnDbType + '\'' +
                ", columnKey='" + columnKey + '\'' +
                '}';
    }
}
