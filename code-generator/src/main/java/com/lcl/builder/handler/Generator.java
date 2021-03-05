package com.lcl.builder.handler;

import cn.hutool.core.io.FileUtil;
import com.lcl.builder.util.FileUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 *  * 代码生成器的核心处理类
 *
 * @author Administrator
 * @date 2020/12/117:44
 */
public class Generator {
    private String templatePath;//模板路径
    private String outPath;//代码生成路径
    private Configuration cfg;

    public Generator(String templatePath, String outPath) throws IOException {
        this.templatePath = templatePath;
        this.outPath = outPath;
        //实例化configuration()
        cfg = new Configuration(Configuration.VERSION_2_3_30);
        // 指定模板加载器
        FileTemplateLoader loader = new FileTemplateLoader(new File(templatePath));
        cfg.setTemplateLoader(loader);
    }

    /**
     * 代码生成
     * 1. 扫描模板路径下的所有模板
     * 2. 对每个模板进行代码填充生成(数据模型)
     */
    public void scanAndGenerator(Map<String, Object> dataModel) throws Exception {
        //1. 根据模板路径找到此路径下的所有模板文件
        List<File> fileList = FileUtils.searchAllFiles(new File(templatePath));

        //2. 每个模板文件生成
        for (File file : fileList) {
            executeGenerator(dataModel, file);
        }

    }

    /**
     * 对模板进行文件生成
     * @param dataModel
     * @param file
     *      模板文件：c: com.example.abc.java
     */
    private void executeGenerator(Map<String, Object> dataModel, File file) throws IOException, TemplateException {
        //1. 文件路径的处理  (E:\模板\${path1}\${path2}\${path3}\${ClassName}.java})
        System.out.println("file = "+file);
        // 得到绝对路径
        String templateFileName = file.getAbsolutePath().replace(this.templatePath, "");//得到绝对路径
        System.out.println("绝对路径：" + templateFileName);
        String outFileName = processTemplateString(templateFileName,dataModel);
        //2. 读取文件模板
        Template template = cfg.getTemplate(templateFileName);
        template.setOutputEncoding("utf-8");
        //3. 创建输出的模板文件
        File file1 = FileUtils.mkdir(outPath, outFileName);
        //4. 模板处理（文件生成）
        FileWriter fileWriter = new FileWriter(file1);
        template.process(dataModel, fileWriter);
        fileWriter.close();

    }

    private String processTemplateString(String templateFileName, Map<String, Object> dataModel) throws IOException, TemplateException {
        StringWriter out = new StringWriter();
        Template template = new Template("name1", new StringReader(templateFileName), cfg);
        System.out.println("templateFleName: "+templateFileName+", dataModel : "+dataModel);
        template.process(dataModel, out);
        System.out.println(out.toString());
        return out.toString();
    }


}
