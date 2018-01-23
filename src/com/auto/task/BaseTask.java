package com.auto.task;

import java.util.List;

/**
 * 包: com.auto.task
 * 源文件:BaseTask.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月22日
 */
public abstract class BaseTask
{

    public abstract String  getPackage();

    public abstract String getActivity();

    public abstract List<String> getAndroidDevices();

    public abstract void excute() throws Exception;


}
