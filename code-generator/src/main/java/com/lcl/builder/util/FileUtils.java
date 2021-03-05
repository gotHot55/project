package com.lcl.builder.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2020/12/210:36
 */
public class FileUtils {
    /**
     *
     * @param outPath 代码生成路径
     * @param outFileName 文件名
     * @return
     */
    public static File mkdir(String outPath, String outFileName) {

        File file = new File(outPath);

        return file;
    }

     public static List<File> searchAllFiles(File path) throws Exception {
        List<File> collector = new ArrayList<>();
        File dir = new File(String.valueOf(path));
        searchFiles(dir,collector);
         return collector;
    }
 
    private static void searchFiles(File dir,List<File> collector){
       File[] files = dir.listFiles();
        for (File f : files) {
            if(f.isFile()){
                collector.add(f);
            }else {
                searchFiles(f,collector);
            }
        }
    }

}
