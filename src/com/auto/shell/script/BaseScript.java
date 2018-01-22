package com.auto.shell.script;

import io.appium.java_client.android.AndroidDriver;

/**
 * 包: com.auto.shell.script
 * 源文件:BaseScript.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月22日
 */
public abstract class BaseScript
{
    protected  AndroidDriver driver;

    public abstract void operateProcess() throws Exception;

    protected void rollUp(int startY,int endY){
        driver.swipe(100, startY, 100, endY, 1000);
    }

    protected abstract void rollPageInCurrent() throws InterruptedException;
}
