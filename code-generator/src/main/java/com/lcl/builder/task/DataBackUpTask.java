package com.lcl.builder.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;

/**
 * Todo
 *定时执行数据库备份任务
 * @author Administrator
 * @date 2020/12/1411:15
 */
//@Component
@EnableScheduling  // 开启对定时任务的支持
@Async  // 异步处理
public class DataBackUpTask {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.datasource.dbName}")
    private String dbName;

    @Value("${spring.datasource.username}")
    private String username; // 数据库登录账号

    @Value("${spring.datasource.password}")
    private String pwd; // 数据库登录密码

    @Value("${spring.datasource.backupPath}")
    private String path; // 备份文件保存路径

// 每天凌晨2点出发数据库备份，需要将mysql的bin目录配置到环境变量path中，以便调用mysqldump指令
    @Scheduled(cron="0 0 2 * * ?")
    public void run(){
        File fl = new File(path);

        if (!fl.exists()) {
            fl.mkdirs();
        }

// 注意：mysqldump.exe路径中不能含有空格，我将这个文件拷贝到了项目中
        try {
            String pathExe = this.getClass().getClassLoader().getResource("").getPath();
            if (pathExe.startsWith("/")) {
                pathExe = pathExe.substring(1);
            }
            String cmd = "cmd /c " + pathExe + "mysqldump -u " + username + " -p" + pwd + " --databases " + dbName + " > " + path + "\\" + dbName + "_" + System.currentTimeMillis() + ".sql";
            Process process = Runtime.getRuntime().exec(cmd);
            if (process.waitFor() == 0) {
                logger.info(LocalDateTime.now() + " 备份数据库成功...");
            }
        } catch (Exception e) {
            logger.info(LocalDateTime.now() + " 备份数据库失败...");
            e.printStackTrace();
        }
    }
}
