package com.lcl.builder.model;

import lombok.Data;

import java.util.List;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/12/116:35
 */
@Data
public class Table {
    private String name;//表名称
    private String name2;//构建后的表名称
    private String commit;//介绍
    private String key;//主键列
    private List<Column> columns;//列集合

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", name2='" + name2 + '\'' +
                ", commit='" + commit + '\'' +
                ", key='" + key + '\'' +
                ", columns=" + columns +
                '}';
    }
}
