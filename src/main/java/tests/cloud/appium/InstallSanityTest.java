package tests.cloud.appium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import tests.BaseTest;

import java.net.MalformedURLException;
import java.net.URL;

public class InstallSanityTest extends BaseTest {
    protected AndroidDriver<AndroidElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();

    @Before
    public void setUp() throws MalformedURLException {
        dc.setCapability("testName", "Appium Quick Start Test");
        dc.setCapability("accessKey", this.accessKey);
        dc.setCapability("deviceQuery", "@serialNumber='" + this.deviceSN + "'");
        dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
        driver = new AndroidDriver<>(new URL(this.url + "/wd/hub"), dc);
    }

    @Test
    public void quickStartAndroidNativeDemo() {

    }

    @Test
    public void installInstrumentedFromPath() {

    }

    @Test
    public void installNonInstrumentedFromPath() {

    }

    @Test
    public void installInstrumentedFromAppManager() {

    }

    @Test
    public void installNonInstrumentedFromAppManager() {

    }

    @After
    public void tearDown() {
        System.out.println(QuickStart.class.getName() + ": Report URL for device " + this.deviceSN + ": "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }
}
