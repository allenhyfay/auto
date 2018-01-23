package com.auto.shell.script;

import com.auto.web.bo.UniqueReadBo;
import com.auto.web.service.UniqueReadService;
import com.jeeframework.core.context.support.SpringContextHolder;
import com.jeeframework.util.encrypt.MD5Util;
import io.appium.java_client.android.AndroidDriver;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 包: com.auto.shell.script
 * 源文件:BaseScript.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月22日
 */
public abstract class BaseScript
{
    protected  AndroidDriver driver;

    protected String devices;

    protected UniqueReadService uniqueReadService;

    private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

    public abstract void operateProcess() throws Exception;

    protected void rollUp(int startY,int endY){
        driver.swipe(100, startY, 100, endY, 1000);
    }

    protected abstract void rollPageInCurrent() throws InterruptedException;

    public abstract String getCurrenTitle();

    public BaseScript()
    {
        this.uniqueReadService = SpringContextHolder.getBean("uniqueReadService");
    }

    protected boolean isEmpty(String title){
        String md5=getUniqueValue(title,devices,new Date());
        if(!uniqueReadService.isNotEmpty(md5))
            return true;
        return false;
    }

    protected void add(String title){
        String md5=getUniqueValue(title,devices,new Date());
        UniqueReadBo uniqueReadBo=new UniqueReadBo();
        uniqueReadBo.setUuid(md5);
        uniqueReadBo.setDevices(devices);
        uniqueReadBo.setTitle(title);
        uniqueReadService.add(uniqueReadBo);
    }

    private  String getDate(Date time){
        return simpleDateFormat.format(time);
    }

    private  String getUniqueValue(String title,String device,Date time){
        return  MD5Util.encrypt(title+device+getDate(time));
    }


}
