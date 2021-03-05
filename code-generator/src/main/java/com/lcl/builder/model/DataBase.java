package com.lcl.builder.model;

import lombok.Data;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/12/116:18
 */
@Data
public class DataBase {
    //数据库连接url模板
    private static String sqlUrl = "jdbc:mysql://[ip]:[port]/[db]?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

    private String dbName;//数据库名称
    private String dbType;//数据库类型
    private String driver;//数据库驱动
    private String userName;//用户名
    private String password;//密码
    private String url;//数据库连接url

    public DataBase() {
    }

    public DataBase(String dbType) {
        this(dbType, "127.0.0.1", "3306", "");
    }
    public DataBase(String dbType,String db) {
        this(dbType, "127.0.0.1", "3306", db);
    }

    public DataBase(String dbType, String ip, String port, String db) {
        this.dbType = dbType;
        this.dbName = db;
        if ("MYSQL".endsWith(dbType.toUpperCase())) {
            this.driver = "com.mysql.cj.jdbc.Driver";
            this.url = sqlUrl.replace("[ip]", ip).replace("[port]", port).replace("[db]", db);
        }
    }

}
