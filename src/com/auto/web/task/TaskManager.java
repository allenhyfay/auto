package com.auto.web.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 包: com.auto.web.task
 * 源文件:TaskManager.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月23日
 */
@Component("task")
public class TaskManager
{
    @Scheduled(cron = "0 0/1 * * * *")
    public void runTask(){
        System.out.println("====================");
    }
}
