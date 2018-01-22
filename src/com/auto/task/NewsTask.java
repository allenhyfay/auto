package com.auto.task;

import com.auto.bo.AndroidDriverStatus;
import com.auto.driver.DriverManager;
import com.auto.shell.script.BaseScript;
import com.auto.shell.script.NewsScript;
import com.gargoylesoftware.htmlunit.WebConsole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 包: com.allen.task
 * 源文件:NewsTask.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月15日
 */
public class NewsTask extends BaseTask
{
    private DriverManager driverManager;

    private AndroidDriverStatus androidDriverStatus;

    public void excute() throws Exception{

        driverManager =new  DriverManager(this);
        androidDriverStatus = driverManager.getEnableDriver();
        if (androidDriverStatus == null) {
            return;
        }
        BaseScript newsScript = new NewsScript(androidDriverStatus.getAndroidDriver());

        try {

            Thread.currentThread().sleep(15000);

            newsScript.operateProcess();
        } catch (Exception e) {
            //boolean isKill = driverManager.stopAppium(androidDriverStatus);
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public String getPackage()
    {
        return "com.martian.hbnews";
    }

    @Override
    public String getActivity()
    {
        return ".activity.MartianAppStart";
    }

    @Override
    public List<String> getAndroidDevices()
    {
        return Arrays.asList(new String[]{"HKR4C15817004771"});
    }
}
