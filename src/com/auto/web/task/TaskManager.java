package com.auto.web.task;

import com.auto.task.BaseTask;
import com.auto.task.NewsTask;
import com.auto.web.service.UniqueReadService;
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

    @Scheduled(cron = "0 10 14 * * *")
    public void runNewsTask(){
        BaseTask task=null;
        try
        {
            task = new NewsTask();
            task.excute();
        }catch (Exception e){
            try
            {
                Thread.sleep(10000);
            }
            catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }
            runNewsTask();
        }

    }
    @Scheduled(cron = "0 14 14 * * *")
    public void runTaoNewsTask(){

    }
}
