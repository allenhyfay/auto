package com.auto.shell.script;


import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

/**
 * 包: com.allen.linux.script
 * 源文件:NewsScript.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月15日
 */
public class NewsScript extends BaseScript
{

    public NewsScript(AndroidDriver driver,String devices)
    {
        super();
        this.driver=driver;
        this.devices=devices;
    }


    public void operateProcess() throws Exception{
        try
        {
            System.out.println(
                    "==========================================================");
            while (true)
            {
                WebElement element = driver
                        .findElementById("com.martian.hbnews:id/irc");
                List<WebElement> elements = element.findElements(
                        By.className("android.widget.FrameLayout"));
                WebElement layoutElement = elements.get(0);
                WebElement ggout = layoutElement.findElement(
                        By.id("com.martian.hbnews:id/news_source"));
                if (ggout.isDisplayed() && ggout.getText().indexOf("广告") > -1)
                {
                    System.out.println("========= 广告");
                    rollUp(600,400);
                    continue;
                }
                else
                {
                    WebElement element2 = layoutElement.findElement(
                            By.id("com.martian.hbnews:id/news_summary_title_tv"));

                    String title=element2.getText();
                    System.out.println(title +"  是否抓过:"+(!isEmpty(title)));
                    if(isEmpty(title))
                    {
                        add(title);
                        element2.click();
                        rollPageInCurrent();
                        Thread.currentThread().sleep(500);
                    }
                    rollUp(600, 400);
                }
            }
        }catch (Exception e){
            System.out.println("===== error:"+e.getMessage()+"  driver close.");
            driver.quit();
            throw e;
        }
    }


    /**
     * 内容滑动并退出
     * @throws InterruptedException
     */
    protected void rollPageInCurrent() throws InterruptedException
    {
        long startTime=System.currentTimeMillis();
        boolean isOpen=false;
        Random rand = new Random();

        int rollcount=0;
        while (true&&rollcount<30){
            rollcount++;
            Thread.currentThread().sleep(8000);
            rollUp(600,300);
            long endTime=System.currentTimeMillis();
            if(endTime-startTime > 100000)
                break;
        }
        driver.findElementByClassName("android.widget.ImageButton").click();
    }

    @Override
    public String getCurrenTitle()
    {
        return null;
    }
}
