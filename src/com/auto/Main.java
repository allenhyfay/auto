package com.auto;

import com.auto.task.NewsTask;

/**
 * 包: com.auto
 * 源文件:Main.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月18日
 */
public class Main
{
    public static void main(String[] args)
    {
        NewsTask task=null;
        try
        {
            task = new NewsTask();
            task.excute();
        }catch (Exception e){
            try
            {
                Thread.sleep(10000);
                task.close();
            }
            catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }

            main(args);
        }
    }
}
