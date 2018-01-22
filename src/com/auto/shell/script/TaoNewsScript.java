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

    public TaoNewsScript(AndroidDriver driver)
    {
        this.driver=driver;
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

                WebElement currentElement = webElement.findElements(
                        By.id("com.coohua.xinwenzhuan:id/tab_feed__item_img_multi"))
                        .get(0);

                currentElement.click();

                rollPageInCurrent();

                int height = currentElement.getSize().getHeight();
                int rollHeight = (600 - height) > 0 ? 600 - height : 400;
                rollUp(600, rollHeight);
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
        while (true&&count<100){
            try{
                Thread.sleep(4000);
                rollUp(600,400);

                WebElement element= driver.findElementById("com.coohua.xinwenzhuan:id/news_detail_extra");
                if(element.isDisplayed()){
                    System.out.println("=====查看全文");
                    element.click();
                }
                WebElement likeElement=driver.findElementById("com.coohua.xinwenzhuan:id/news_detail_prefer");
                if(likeElement.isDisplayed()){
                    rollUp(600, 400);
                    break;
                }
                count++;
            }catch (Exception e){}
        }

        driver.findElementById("com.coohua.xinwenzhuan:id/xlxl_actionbar_up").click();

    }
}
