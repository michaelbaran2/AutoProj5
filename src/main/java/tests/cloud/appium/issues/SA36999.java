package tests.cloud.appium.issues;


import com.experitest.appium.SeeTestCapabilityType;
import com.experitest.appium.SeeTestClient;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.*;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import tests.BaseTest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class SA36999 extends BaseTest {
    protected AndroidDriver<AndroidElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();
    SeeTestClient client;

    @Before
    public void setUp() throws MalformedURLException {
        dc.setCapability("testName", "SA36999");
        dc.setCapability("accessKey", this.accessKey);
        dc.setCapability("deviceQuery", "@serialNumber='" + this.deviceSN + "'");
        dc.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);
        dc.setCapability(MobileCapabilityType.APP, "cloud:com.sysinfodroid/com.valenbyte.systeminfo.activities.SplashActivity");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.sysinfodroid");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.valenbyte.systeminfo.activities.SplashActivity");
        driver = new AndroidDriver<>(new URL(this.url + "/wd/hub"), dc);
        client = new SeeTestClient(driver);
    }

    @Test
    public void quickStartAndroidNativeDemo() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        client.startLoggingDevice("longrunoutput" + File.separator + this.deviceSN + "-logcat_" + System.currentTimeMillis() + ".log");
        client.startPerformanceTransactionForApplication("com.sysinfodroid", "4G-average");
        client.click("NATIVE", "xpath=//*[@text='GOT IT']", 0, 1);
        client.click("NATIVE", "xpath=//*[@contentDescription='Open navigation drawer']", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='Benchmark']", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='Start']", 0, 1);
        client.sleep(10000);
        System.out.println(client.endPerformanceTransaction("Wack"));
        client.stopLoggingDevice();
    }

    @After
    public void tearDown() {
        System.out.println(SA36999.class.getName() + ": Report URL for device " + this.deviceSN + ": "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }
}

