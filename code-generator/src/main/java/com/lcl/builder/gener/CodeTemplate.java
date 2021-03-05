package com.lcl.builder.gener;

import com.lcl.builder.handler.GeneratorFacade;
import com.lcl.builder.model.DataBase;
import com.lcl.builder.model.Settings;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/12/210:33
 */
public class CodeTemplate {
    //UI调用程序入口
    /**
     *
     * @param templetPath		模板所在路径
     * @param outpath			选择代码生成路径
     * @param settings			工程配置对象
     * @param db				数据库信息
     */
    private void generator(String templetPath, String outpath, Settings settings, DataBase db) throws Exception {
        GeneratorFacade gf = new GeneratorFacade(templetPath,outpath,settings,db);
        gf.generatorByDataBase();
    }
}
