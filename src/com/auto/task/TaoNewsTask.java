package com.auto.task;

import com.auto.bo.AndroidDriverStatus;
import com.auto.driver.DriverManager;
import com.auto.shell.script.NewsScript;
import com.auto.shell.script.TaoNewsScript;

import java.util.Arrays;
import java.util.List;

/**
 * 包: com.auto.task
 * 源文件:TaoNews.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月22日
 */
public class TaoNewsTask extends BaseTask
{

    private DriverManager driverManager;

    private AndroidDriverStatus androidDriverStatus;

    @Override
    public String getPackage()
    {
        return "com.coohua.xinwenzhuan";
    }

    @Override
    public String getActivity()
    {
        return ".controller.MainActivity";
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
        TaoNewsScript taoNewsScript = new TaoNewsScript(androidDriverStatus.getAndroidDriver());

        try {

            Thread.currentThread().sleep(15000);

            taoNewsScript.operateProcess();
        } catch (Exception e) {
            //boolean isKill = driverManager.stopAppium(androidDriverStatus);
            e.printStackTrace();
            throw e;
        }
    }
}
