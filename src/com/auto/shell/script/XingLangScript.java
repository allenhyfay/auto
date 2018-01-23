package com.auto.shell.script;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * 包: com.auto.shell.script
 * 源文件:XingLangScript.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月23日
 */
public class XingLangScript extends BaseScript
{

    public XingLangScript(AndroidDriver driver,String devices)
    {
        //super();
        this.driver=driver;
        this.devices=devices;
    }

    @Override
    public void operateProcess() throws Exception
    {
        try{

            WebElement webElement=driver.findElementById("com.sohu.infonews:id/tab_bar");

            List<WebElement> webElements=webElement.findElements(By.className("android.widget.RelativeLayout"));

            //要闻
            WebElement yaowenElement=webElements.get(1);

            yaowenElement.click();

            Thread.sleep(1000);

            while (true)
            {
                System.out.printf("=====列表\n");
                //内容
                WebElement contentElements = driver
                        .findElementById("com.sohu.infonews:id/mRecyclerView");

                WebElement firstElement = contentElements.findElements(
                        By.className("android.widget.LinearLayout")).get(0);

                WebElement titleElement=firstElement.findElement(By.id("com.sohu.infonews:id/article_title"));
                System.out.println("title:"+titleElement.getText());
                //if(isEmpty(titleElement.getText())){
                    //add(titleElement.getText());
                titleElement.click();
                    rollPageInCurrent();
               // }
                int elementHeight=firstElement.getSize().getHeight();
                int height=600-elementHeight > 0?600-elementHeight:400;

                rollUp(600,height);
                Thread.sleep(1000);
            }

        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    protected void rollPageInCurrent() throws InterruptedException
    {
        System.out.printf("-----------------------------------------");
        int count=0;
        while (true&&count<20){

            try
            {
             /*   WebElement webElement = driver
                        .findElementById("com.sohu.infonews:id/body_bar");*/

                /*WebElement tipElement = driver
                        .findElementById("com.sohu.infonews:id/refer_tip");
                if (tipElement.isDisplayed())
                {
                    rollUp(600, 200);
                    break;
                }*/
            }catch (Exception e){e.printStackTrace();}
            Thread.sleep(1000);
            rollUp(600,200);
            count++;
        }
        Thread.sleep(1000);
        driver.findElementById("com.sohu.infonews:id/navigation_bar").findElements(By.className("android.widget.ImageView")).get(0).click();
    }

    @Override
    public String getCurrenTitle()
    {
        return null;
    }
}
