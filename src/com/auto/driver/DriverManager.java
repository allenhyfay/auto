package com.auto.driver;


import com.auto.bo.AndroidDriverStatus;
import com.auto.bo.ChromeOption;
import com.auto.shell.ShellProcess;
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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 简单描述：设备控制类,用于设备的初始化,启动,删除,
 * <p>
 *
 * @
 */
public class DriverManager
{
    private static Lock lock=new ReentrantLock();
    public  static List<AndroidDriverStatus> driverList = new ArrayList<AndroidDriverStatus>(); //可用设备
    private static DriverManager instance = null;
    private String devicesName="HKR4C15817004771";
    private  String adbPoint="adb";
    private String appiumPoint = "appium";

    //静态工厂方法
    public static DriverManager getInstance() {
        if (instance == null) {
            instance = new DriverManager();
        }
        return instance;
    }

    public  void close(){
        if(instance!=null)
        {
            instance = null;
        }
    }

    private DriverManager() {
        initDriver();
    }

    private void initDriver() {
        int port = 4723;
        int bp = 4721;
        //调用远程mcss接口获取设备
        List<String> devicesNameList = getAndroidDevices();
        //executeShell(adbPoint+" start-server");

        //启动同等设备数量的appium-server
        for (String devicesName : devicesNameList) {
            //executeShell(adbPoint+" connect " + devicesName);
            //String servicePid = startAppiumService(port, bp, devicesName);

            AndroidDriverStatus androidDriverStatus = new AndroidDriverStatus();
            androidDriverStatus.setPort(port);
            androidDriverStatus.setBp(bp);
            androidDriverStatus.setDeviceName(devicesName);
            driverList.add(androidDriverStatus);
        }

        ChromeOption chromeOption = new ChromeOption();
        chromeOption.setAndroidPackage("com.martian.hbnews");
        chromeOption.setAndroidActivity(".activity.MartianAppStart");//启动页
        getEnableSettingDriverManager("com.martian.hbnews", ".activity.MartianAppStart", chromeOption);

    }

    public AndroidDriver createDrives(ChromeOption chromeOption,int bp,int port){
        AndroidDriver driver = creatDriver(chromeOption.getAndroidPackage(),
                chromeOption.getAndroidActivity(),
                devicesName, chromeOption,
                bp, port);
        return driver;
    }

    private String startAppiumService(int port, int bp, String devicesName) {
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
        String str = appiumPoint+" -p " + port + " -bp " + bp + " --device-name " + devicesName ;
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

    private List<String> getNodePidList(Scanner in) {
        List<String> pidList = new ArrayList<>();
        while (in.hasNext()) {
            String processInf = in.nextLine();
            if (processInf.contains("node")) {
                String[] strings = processInf.split("\\s+");
                pidList.add(strings[1]);
            }
        }
        return pidList;
    }

    /*
     * 获取可用的driver
     */
    public AndroidDriverStatus getEnableDriver() {
        for (AndroidDriverStatus androidDriverStatus : driverList) {
            //if (androidDriverStatus.getStatus().equalsIgnoreCase("0")) {
               // androidDriverStatus.setStatus("1");
                return androidDriverStatus;
           // }
        }
        return null;
    }

    /*
     * 释放设备
     */
    public boolean releaseDriver(String deviceName) {
        for (AndroidDriverStatus androidDriverStatus : driverList) {
            if (androidDriverStatus.getDeviceName().equalsIgnoreCase(deviceName)) {
                androidDriverStatus.setStatus("0");
                return true;
            }
        }
        return false;
    }


    /*
    * kill掉所有的appiumService和由它启动的adb
    */
    public boolean killAppiumServiceAndRelateAdb(String devicesName) {
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

    /*
    * kill掉相应的appiumService和由它启动的adb
    */
    public boolean stopAppium(String pid,String deviceName){
        String adbId = null;
        //记录下指定pid node下的adb
        ShellProcess shellProcess = executeShell("pstree " + pid + " -p");
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

    public boolean stopAppium(AndroidDriverStatus androidDriverStatus) {
        androidDriverStatus.setStatus("10");
        String pid = androidDriverStatus.getServicePid();
        String deviceName = androidDriverStatus.getDeviceName();
        return stopAppium(pid,deviceName);
    }

    /*public void restartDriver(String devicesName) {
        for (AndroidDriverStatus androidDriverStatus : driverList) {
            if (androidDriverStatus.getDeviceName().equalsIgnoreCase(devicesName)) {
                androidDriverStatus.setStatus("10");
                restartAppium(androidDriverStatus);
                break;
            }
        }
    }*/

  /*  //TODO 后续需重构
    public void restartAppium(AndroidDriverStatus androidDriverStatus) {
        int port = androidDriverStatus.getPort();
        int bp = androidDriverStatus.getBp();
        String devicesName = androidDriverStatus.getDeviceName();

        int loop = 0; //循环出错次数
        while (loop < 3) {
            loop++;
            String servicePid = startAppiumService(port, bp, devicesName);
            if (servicePid.equals("")) {
                //启动appium失败,删除node,避免实际上已经启动了appium,但是删除失败,重新启动appium端口占用的错误
                System.out.println("XXXX: pid is empty , kill appium-server");
                killAppiumServiceAndRelateAdb(devicesName);
            } else {
                //启动appium成功
                androidDriverStatus.setServicePid(servicePid);
                break;
            }
        }

        ChromeOption chromeOption = new ChromeOption();
        chromeOption.setAndroidPackage("com.tencent.mm");
        chromeOption.setAndroidActivity(".plugin.webview.ui.tools.WebViewUI");
        chromeOption.setAndroidProcess("com.tencent.mm:tools");
        AndroidDriver driver = creatDriver("com.tencent.mm", ".ui.LauncherUI", devicesName, chromeOption, bp, port);
        if (driver != null) {
            //创建成功
            androidDriverStatus.setStatus("0");
            androidDriverStatus.setAndroidDriver(driver);
            if (enterChatView(driver)) {
                //进入到聊天页面成功
            }
        } else {
            //创建失败
            androidDriverStatus.setStatus("9");
        }
    }*/

    private List<String> getAndroidDevices() {
        List<String> devicesNameList = new ArrayList<>();
        devicesNameList.add(devicesName);
        return devicesNameList;
    }

    /**
     * 简单描述：创建setting driver
     *
     * @param appPackage   : com.android.settings设置 / com.saurik.substrate hook环境
     * @param appActivity: .Settings设置  / .SetupActivity hook环境
     * @return 是否成功
     */
    public void getEnableSettingDriverManager(String appPackage, String appActivity, ChromeOption chromeOption) {
        System.out.println("XXXX: location_mcss : begin create client_1: " + driverList.size());
        for (AndroidDriverStatus androidDriverDao : driverList) {
            System.out.println("XXXX: location_mcss : begin create client_1: " + androidDriverDao.getStatus());
            System.out.println("XXXX: location_mcss : begin create client");
            AndroidDriver driver = creatDriver(appPackage, appActivity, androidDriverDao.getDeviceName(), chromeOption, androidDriverDao.getBp(), androidDriverDao.getPort());
            if (driver != null) {
                //appium driver创建成功
                System.out.println("XXXX: location_mcss : driverManager start weixin success");
                androidDriverDao.setStatus("0");
                androidDriverDao.setBindApp(appPackage);
                androidDriverDao.setAndroidDriver(driver);
                /*if (enterChatView(driver)) {
                    Runnable runnable = new DriverWaitRunable(androidDriverDao);
                    Thread thread = new Thread(runnable);
                    thread.start();
                    androidDriverDao.setThread(thread);
                }*/
            } else {
                androidDriverDao.setStatus("9");
            }
            break;
        }
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
    private AndroidDriver creatDriver(String appPackage, String appActivity, String udid, ChromeOption chromeOption, int bp, int port) {
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

    /**
     * 简单描述：获取执行shell后打印结果
     */
    private Scanner getShellResultContent(Process process) {
        return new Scanner(process.getInputStream());
    }

    public ShellProcess executeShell(String shell) {
        return executeShell(shell, true);
    }

    /**
     * 简单描述：执行shell命令
     *
     * @return Process(shell命令执行后的返回)+status
     */
    public ShellProcess executeShell(String shell, boolean isWait) {
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
}
