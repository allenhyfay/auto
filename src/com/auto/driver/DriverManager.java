package com.auto.driver;


import com.auto.bo.AndroidDriverStatus;
import com.auto.bo.ChromeOption;
import com.auto.shell.CommonService;
import io.appium.java_client.android.AndroidDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单描述：设备控制类,用于设备的初始化,启动,删除,
 * <p>
 *
 * @
 */
public class DriverManager
{
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
        for (AndroidDriverStatus androidDriverDao : driverList) {
            AndroidDriver driver = CommonService.creatDriver(appPackage, appActivity, androidDriverDao.getDeviceName(),
                    chromeOption, androidDriverDao.getBp(), androidDriverDao.getPort(),androidDriverDao.getDeviceName());
            if (driver != null)
            {
                //appium driver创建成功
                androidDriverDao.setStatus("0");
                androidDriverDao.setBindApp(appPackage);
                androidDriverDao.setAndroidDriver(driver);
            }
            break;
        }
    }





}
