package tests.cloud.appium.timing;


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

public class SimpleInstall extends BaseTest {
    protected AndroidDriver<AndroidElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();
    SeeTestClient seetest; //

    @Before
    public void setUp() throws MalformedURLException {
        dc.setCapability("testName", "setMonitorPollingInterval");
        dc.setCapability("accessKey", this.accessKey);
        dc.setCapability("deviceQuery", "@serialNumber='" + this.deviceSN + "'");
        dc.setCapability(MobileCapabilityType.APP, "cloud:com.moonactive.coinmaster/com.moon.coinmaster.android.GameActivity");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.moonactive.coinmaster");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.moon.coinmaster.android.GameActivity");
        driver = new AndroidDriver<>(new URL(this.url + "/wd/hub"), dc);
    }

    @Test
    public void quickStartAndroidNativeDemo() throws InterruptedException {
        Thread.sleep(3000);

    }

    @After
    public void tearDown() {
        System.out.println(SimpleInstall.class.getName() + ": Report URL for device " + this.deviceSN + ": "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }
}

