package com.auto.shell.script;


import com.auto.bo.AndroidDriverStatus;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;

/**
 * 包: com.allen.linux.script
 * 源文件:NewsScript.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月15日
 */
public class NewsScript
{
    private AndroidDriverStatus androidDriverStatus;

    private AndroidDriver driver;

    private String chromeDriverId;

    public NewsScript(AndroidDriverStatus androidDriverStatus)
    {
        this.androidDriverStatus = androidDriverStatus;
        this.driver=androidDriverStatus.getAndroidDriver();
        this.chromeDriverId=androidDriverStatus.getServicePid();
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
                    rollUp();
                    continue;
                }
                else
                {
                    WebElement element2 = layoutElement.findElement(
                            By.id("com.martian.hbnews:id/news_summary_title_tv"));
                    element2.click();
                    rollPageInCurrent();
                    Thread.currentThread().sleep(500);
                    rollUp();
                }
            }
        }catch (Exception e){
            System.out.println("===== error:"+e.getMessage()+"  driver close.");
            driver.quit();
            throw e;
        }
    }

    /**
     * 向上滚动
     */
    private void rollUp(){
        driver.swipe(100, 600, 100, 400, 1000);
    }

    /**
     * 内容滑动并退出
     * @throws InterruptedException
     */
    private void rollPageInCurrent() throws InterruptedException
    {
        long startTime=System.currentTimeMillis();
        while (true){

            driver.swipe(100,600,100,300,1000);
            Thread.currentThread().sleep(2000);

            long endTime=System.currentTimeMillis();
            if(endTime-startTime > 30000)
                break;
        }
        driver.findElementByClassName("android.widget.ImageButton").click();
    }


}
