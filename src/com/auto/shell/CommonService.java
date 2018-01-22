package com.auto.shell;

import com.auto.bo.ChromeOption;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 包: com.auto.shell
 * 源文件:CommonService.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月22日
 */
public class CommonService
{

    /**
     * 启动 appium
     * @param port
     * @param bp
     * @param devicesName
     * @return
     */
    public static String startAppiumService(int port, int bp, String devicesName) {
       /* lock.lock();
        List<String> nodeProcessIdBe = new ArrayList<>();
        List<String> nodeProcessIdAf = new ArrayList<>();
        String pid = "";
        try {
            Process processBe = Runtime.getRuntime().exec("ps -aux");
            processBe.waitFor();
            Scanner in = new Scanner(processBe.getInputStream());
            nodeProcessIdBe.addAll(getNodePidList(in));
        } catch (IOException | InterruptedException ioe) {
            ioe.printStackTrace();
        }
*/
        String str = "appium -p " + port + " -bp " + bp + " --device-name " + devicesName ;
        try {
            Runtime.getRuntime().exec(str);
            try {
                Thread.sleep(6 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        /*//遍历系统进程,获取启动的node.exe进程号
        try {
            Process processAf = Runtime.getRuntime().exec("ps -aux");
            processAf.waitFor();
            Scanner in = new Scanner(processAf.getInputStream());
            nodeProcessIdAf.addAll(getNodePidList(in));
            int nodeProcessIdAfLength = nodeProcessIdAf.size();
            for (int i = nodeProcessIdAfLength - 1; i >= 0; i--) {
                String string = nodeProcessIdAf.get(i);
                if (nodeProcessIdBe.contains(string)) {
                    nodeProcessIdAf.remove(i);
                }
            }

            if (nodeProcessIdAf.size() > 0) {
                pid = nodeProcessIdAf.get(0);
            }
        } catch (IOException | InterruptedException ioe) {
            ioe.printStackTrace();
        } finally {
            lock.unlock();
        }*/
        //return pid;

        return "11111";
    }




    /*
    * kill掉所有的appiumService和由它启动的adb
    */
    public static boolean killAppiumServiceAndRelateAdb(String devicesName) {
        System.out.println("XXXX: killNode");
        List<String> nodePidList = new ArrayList<>();
        //取出当前系统里所有的node
        ShellProcess shellProcess = executeShell("ps -aux");
        if (shellProcess.isSuccessful()) {
            Scanner scanner = getShellResultContent(shellProcess.getProcess());
            while (scanner.hasNext()) {
                String processInf = scanner.nextLine();
                if (processInf.contains("node")) {
                    if (processInf.contains(devicesName)) {
                        String[] strings = processInf.split("\\s+");
                        nodePidList.add(strings[1]);
                    }
                }
            }

            for (String nodePid : nodePidList) {
                String nodeSubadbId = "";
                ShellProcess shellprocesstres = executeShell("pstree " + nodePid + " -p");
                if (shellprocesstres.isSuccessful()) {
                    Scanner inNodePid = getShellResultContent(shellProcess.getProcess());
                    while (inNodePid.hasNext()) {
                        String processInf = inNodePid.nextLine();
                        if (processInf.contains("adb")) {
                            String id = processInf.trim().split("adb")[1];
                            id = id.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& ;*（）——+|{}【】‘；：”“’。，、？|-]", "");
                            nodeSubadbId = id;
                            break;
                        }
                    }
                    if (!nodeSubadbId.equals("")) {
                        executeShell("kill -s 9 " + nodeSubadbId);
                    }
                    executeShell("kill -s 9 " + nodePid);
                }
            }
        }
        return true;
    }


    public static ShellProcess executeShell(String shell) {
        return executeShell(shell, true);
    }

    /**
     * 简单描述：执行shell命令
     *
     * @return Process(shell命令执行后的返回)+status
     */
    public static ShellProcess executeShell(String shell, boolean isWait) {
        ShellProcess shellProcess = new ShellProcess();
        try {
            Process process = Runtime.getRuntime().exec(shell);
            if (isWait) {
                process.waitFor();
                shellProcess.setProcess(process);
                shellProcess.setSuccessful(true);
            } else {
                Thread.sleep(6 * 1000);
                shellProcess.setProcess(process);
                shellProcess.setSuccessful(true);
            }
        } catch (IOException | InterruptedException e) {
            shellProcess.setProcess(null);
            shellProcess.setSuccessful(false);
            e.printStackTrace();
        }
        return shellProcess;
    }

    /**
     * 简单描述：获取执行shell后打印结果
     */
    private static Scanner getShellResultContent(Process process) {
        return new Scanner(process.getInputStream());
    }

    /**
     * 简单描述：创建 driver
     *
     * @param appPackage   app包名
     * @param appActivity  app启动类
     * @param udid         设备名称
     * @param chromeOption chrome设置
     * @param bp
     * @param port
     * @return 创建成功返回driver
     */
    public static AndroidDriver creatDriver(String appPackage, String appActivity,
            String devicesName, ChromeOption chromeOption, int bp, int port,
            String udid) {
        DesiredCapabilities capability = new DesiredCapabilities();
        //capability.setCapability("app", "");
        capability.setCapability("appPackage", appPackage);
        capability.setCapability("appActivity", appActivity);
        capability.setCapability("deviceName", devicesName);
        //capability.setCapability("udid", udid);
        //capability.setCapability("fullReset", "false");
        //capability.setCapability("noReset", "true");
        //capability.setCapability("bootstrapPort", bp + "");
        //capability.setCapability("unicodeKeyboard", "true");
        //capability.setCapability("resetKeyboard", "true");

        if (chromeOption != null) {
            //关键是加上这段
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("androidPackage", chromeOption.getAndroidPackage());
            options.setExperimentalOption("androidUseRunningApp", true);
            options.setExperimentalOption("androidActivity", chromeOption.getAndroidActivity());
            options.setExperimentalOption("androidProcess", chromeOption.getAndroidProcess());
            capability.setCapability(ChromeOptions.CAPABILITY, options);
        }
        try {
            AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"),
                    capability);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            return driver;
        } catch (MalformedURLException e) {
            System.out.println("XXXX: erorr initAppium" + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    /*
   * kill掉相应的appiumService和由它启动的adb
   */
    public boolean stopAppium(String pid,String deviceName){
        String adbId = null;
        //记录下指定pid node下的adb
        ShellProcess shellProcess = CommonService.executeShell("pstree " + pid + " -p");
        if (shellProcess.isSuccessful()) {
            Scanner in = getShellResultContent(shellProcess.getProcess());
            if (in.hasNext()) {
                while (in.hasNext()) {
                    String processInf = in.nextLine();
                    if (processInf.contains("adb")) {
                        String id = processInf.trim().split("adb")[1];
                        id = id.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& ;*（）——+|{}【】‘；：”“’。，、？|-]", "");
                        adbId = id;
                        break;
                    }
                }
                //Kill掉node
                executeShell("kill -s 9 " + pid);
                //确认是否删除
                Scanner in2 = getShellResultContent(executeShell("pstree " + pid + " -p").getProcess());
                if (in2.hasNext()) {
                    //如果根据pid没有删除成功,强制删除一次
                    System.out.println("XXXX: stopAppium3" + pid);
                    killAppiumServiceAndRelateAdb(deviceName);
                }
                //kill掉删除不掉的adb
                if (adbId != null) {
                    ShellProcess process3 = executeShell("kill -s 9 " + adbId);
                }
            }
        }
        return true;
    }
}
