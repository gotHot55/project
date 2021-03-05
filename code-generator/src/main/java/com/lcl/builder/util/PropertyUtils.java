package com.lcl.builder.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/12/210:37
 */
public class PropertyUtils {
    //创建用户自定义配置存储Map集合
    public static Map<String, String> customMap = new HashMap<>();
    //随着类被加载而执行，也就是说只会被执行一次
    static {
        //获取配置文件所在路径File对象
        File dir = new File("properties");
        try {
            //获取路径下全部文件
            List<File> files = FileUtils.searchAllFiles(new File(dir.getAbsolutePath()));
            //遍历全部文件
            for (File file : files) {
                //只处理.properties后缀的文件
                if (file.getName().endsWith(".properties")) {
                    Properties prop = new Properties();
                    //加载自定义配置文件
                    prop.load(new FileInputStream(file));
                    //存储自定义配置信息到Map集合
                    customMap.putAll((Map) prop);
                }
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }


    }
    
}


















