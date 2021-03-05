package com.lcl.builder.handler;

import com.lcl.builder.model.DataBase;
import com.lcl.builder.model.Settings;
import com.lcl.builder.model.Table;
import com.lcl.builder.util.DataBaseUtils;
import com.lcl.builder.util.PropertyUtils;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器的核心入口类
 *
 * @author Administrator
 * @date 2020/12/210:08
 */
public class GeneratorFacade {
    private String templatePath;
    private String outPath;
    private Settings settings;
    private DataBase db;
    private Generator generator;

    public GeneratorFacade(String templatePath, String outPath, Settings settings, DataBase db) throws IOException {
        this.templatePath = templatePath;
        this.outPath = outPath;
        this.settings = settings;
        this.db = db;
        this.generator = new Generator(templatePath, outPath);
    }

    /**
     * 1.准备数据模型
     * 2.调用核心处理类完成代码生成工作
     */
    public void generatorByDataBase() throws Exception {
        List<Table> tables = DataBaseUtils.getDbInfo(db);
        for (Table table : tables) {
            Map<String, Object> dataModel = getDataModel(table);
            generator.scanAndGenerator(dataModel);
        }
    }
    /**
     * 根据table对象获取数据模型
     */
    private Map<String, Object> getDataModel(Table table) {
        HashMap<String, Object> dataModel = new HashMap<>();
        // 1.自定义配置
        dataModel.putAll(PropertyUtils.customMap);
        dataModel.put("table", table);
        dataModel.putAll(this.settings.getSettingMap());
        dataModel.put("ClassName", table.getName2());
        return dataModel;
    }

}
