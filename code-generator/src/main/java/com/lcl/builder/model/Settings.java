package com.lcl.builder.model;


import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/12/116:54
 */

public class Settings {
    private String project = "example";//项目名称
    private String pPackage = "com.example.demo";//包名
    private String projectComment;
    private String author;
    private String path1 = "com";//路径1
    private String path2 = "example";//路径2
    private String path3 = "demo";//路径3
    private String pathAll;//完整路径

    public Settings(String project, String pPackage, String projectComment, String author,
                    String path1, String path2, String path3, String pathAll) {
        if (StringUtils.isNotBlank(project)) {
            this.project = project;
        }
        if (StringUtils.isNotBlank(pPackage)) {
            this.pPackage = pPackage;
        }
        this.projectComment = projectComment;
        this.author = author;
        // 把包名拆分为路径（‘.’需要使用转义符号‘\’才能表示为字符串“.”，并且在IDEA中要用“\\”）
        String[] paths=pPackage.split("\\.");
        path1 = paths[0];
        path2 = paths.length > 1 ? paths[1] : path2;
        path3 = paths.length > 2 ? paths[2] : path3;
        //把包名转换为路径名
        pathAll = pPackage.replace(".", "/");
    }

    public Map<String, Object> getSettingMap() {
        HashMap<String, Object> map = new HashMap<>();
        // 获取Settings的class信息，并获得其中属性字段信息
        Field[] fields = Settings.class.getDeclaredFields();
        for (Field field : fields) {
            // 允许访问‘private’修饰的字段
            field.setAccessible(true);

            try {
                // 构造 key-value 键值对，表示 属性名-属性值,存入Map集合
                map.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
