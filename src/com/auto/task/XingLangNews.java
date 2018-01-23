package com.auto.task;

import com.auto.bo.AndroidDriverStatus;
import com.auto.driver.DriverManager;
import com.auto.shell.script.BaseScript;
import com.auto.shell.script.NewsScript;
import com.auto.shell.script.XingLangScript;

import java.util.Arrays;
import java.util.List;

/**
 * 包: com.auto.task
 * 源文件:XingLangNews.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月23日
 */
public class XingLangNews extends BaseTask
{
    private DriverManager driverManager;

    private AndroidDriverStatus androidDriverStatus;

    @Override
    public String getPackage()
    {
        return "com.sohu.infonews";
    }

    @Override
    public String getActivity()
    {
        return "com.sohu.quicknews.splashModel.activity.SplashActivity";
        //        1877526c com.sohu.infonews/com.sohu.quicknews.commonLib.activity.ActionA
        //ctivity
    }

    @Override
    public List<String> getAndroidDevices()
    {
        return Arrays.asList(new String[]{"HKR4C15817004771"});
    }

    @Override
    public void excute() throws Exception
    {
        driverManager =new DriverManager(this);
        androidDriverStatus = driverManager.getEnableDriver();
        if (androidDriverStatus == null) {
            return;
        }

        try {
            BaseScript newsScript = new XingLangScript(androidDriverStatus.getAndroidDriver()
                    ,androidDriverStatus.getDeviceName());
            System.out.printf("=================12");
            Thread.sleep(15000);

            newsScript.operateProcess();
        } catch (Exception e) {
            //boolean isKill = driverManager.stopAppium(androidDriverStatus);
            e.printStackTrace();
            throw e;
        }
    }
}
