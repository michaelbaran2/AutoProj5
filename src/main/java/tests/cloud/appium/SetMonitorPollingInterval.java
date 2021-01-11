package tests.cloud.appium;


import com.experitest.appium.SeeTestCapabilityType;
import com.experitest.appium.SeeTestClient;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import tests.BaseTest;

import java.net.MalformedURLException;
import java.net.URL;

public class SetMonitorPollingInterval extends BaseTest {
    protected AndroidDriver<AndroidElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();
    SeeTestClient seetest;


    @Before
    public void setUp() throws MalformedURLException {
        dc.setCapability("testName", "SetMonitorPollingInterval");
        dc.setCapability("accessKey", this.accessKey);
        dc.setCapability("deviceQuery", "@serialNumber='" + this.deviceSN + "'");
        dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
        driver = new AndroidDriver<>(new URL(this.url + "/wd/hub"), dc);
        seetest = new SeeTestClient(driver);
    }

    @Test
    public void quickStartAndroidNativeDemo() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        for (int i = 0; i < 300; i++) {
            seetest.setMonitorPollingInterval(500);
        }
    }

    @After
    public void tearDown() {
        System.out.println(SetMonitorPollingInterval.class.getName() + ": Report URL for device " + this.deviceSN + ": "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }

}

