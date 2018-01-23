package com.auto.shell.script;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 包: com.auto.shell.script
 * 源文件:TaoNewsScript.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月22日
 */
public class TaoNewsScript extends BaseScript
{

    public TaoNewsScript(AndroidDriver driver,String devices)
    {
        super();
        this.driver=driver;
        this.devices=devices;
    }

    @Override
    public void operateProcess() throws Exception
    {
        while (true)
        {
            try
            {
                WebElement webElement = driver.findElementById(
                        "com.coohua.xinwenzhuan:id/home_news_list");

                if(!webElement.isDisplayed()){
                    System.out.println("===== 跳过");
                    rollUp(600, 400);
                    continue;
                }

                WebElement currentElement = webElement.findElements(
                        By.id("com.coohua.xinwenzhuan:id/tab_feed__item_img_multi"))
                        .get(0);
                if(!webElement.isDisplayed()){
                    System.out.println("===== 跳过2");
                    rollUp(600, 400);
                    continue;
                }

                String title=currentElement.findElement(By.id("com.coohua.xinwenzhuan:id/tab_feed__item_img_multi_title")).getText();
                if(isEmpty(title))
                {
                    add(title);
                    currentElement.click();

                    rollPageInCurrent();
                }

                rollUp(600, 100);
                Thread.currentThread().sleep(6000);
            }catch (Exception e){
                rollUp(600, 400);
            }
        }
    }

    @Override
    protected void rollPageInCurrent() throws InterruptedException
    {
        long startTime=System.currentTimeMillis();
        int count=0;
        boolean isAll=false;
        while (true&&count<100){
            try{
                if(count>0)
                {
                    rollUp(800, 250);
                }

                if(!isAll&&count>3)
                {
                    System.out.println("=============");
                    WebElement element = driver.findElementById(
                            "com.coohua.xinwenzhuan:id/news_detail_look_more");
                    if (element.isDisplayed())
                    {
                        System.out.println("=====查看全文");
                        element.click();
                        isAll=true;
                    }
                }
                if(isAll&&count>3)
                {
                    WebElement likeElement = driver.findElementById(
                            "com.coohua.xinwenzhuan:id/news_detail_prefer");
                    if (likeElement.isDisplayed())
                    {
                        System.out.println("=====结束");
                        rollUp(600, 100);
                        Thread.sleep(2000);
                        break;
                    }
                }
                count++;
            }catch (Exception e){}
        }
        rollUp(600, 100);
        driver.findElementById("com.coohua.xinwenzhuan:id/xlxl_actionbar_up").click();

    }

    @Override
    public String getCurrenTitle()
    {
        return null;
    }
}
