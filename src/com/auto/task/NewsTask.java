package com.auto.task;

import com.auto.bo.AndroidDriverStatus;
import com.auto.driver.DriverManager;
import com.auto.shell.script.NewsScript;
import com.gargoylesoftware.htmlunit.WebConsole;

/**
 * 包: com.allen.task
 * 源文件:NewsTask.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月15日
 */
public class NewsTask
{
    private DriverManager driverManager;

    private AndroidDriverStatus androidDriverStatus;

    public void excute() throws Exception{

        driverManager = DriverManager.getInstance();
        androidDriverStatus = driverManager.getEnableDriver();
        if (androidDriverStatus == null) {
            return;
        }
        NewsScript newsScript = new NewsScript(androidDriverStatus);

        try {

            Thread.currentThread().sleep(10000);

            newsScript.operateProcess();
        } catch (Exception e) {
            //boolean isKill = driverManager.stopAppium(androidDriverStatus);
            e.printStackTrace();
            throw e;
        }
    }

    public void close() {
        driverManager.close();
    }


}
