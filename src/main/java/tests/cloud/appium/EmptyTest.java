package tests.cloud.appium;


import base.CloudAPI.Session;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
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

public class EmptyTest extends BaseTest {
    protected AndroidDriver<AndroidElement> androidDriver = null;
    protected IOSDriver<IOSElement> iosDriver = null;
    protected String deviceOs;
    DesiredCapabilities dc = new DesiredCapabilities();

    @Before
    public void setUp() throws MalformedURLException, UnirestException {
        dc.setCapability("testName", "Do nothing");
        dc.setCapability("accessKey", this.accessKey);
        dc.setCapability("deviceQuery", "@serialNumber='" + this.deviceSN + "'");
        Session session = new Session(this.url, this.accessKey);
        deviceOs = session.getOs(this.deviceSN);
        Assert.assertNotNull(deviceOs);
        if (deviceOs.equalsIgnoreCase("android")) {
            androidDriver = new AndroidDriver<>(new URL(this.url + "/wd/hub"), dc);
        }
        else {
            iosDriver = new IOSDriver<>(new URL(this.url + "/wd/hub"), dc);
        }
    }

    @Test
    public void emptyTest() throws InterruptedException {
        Thread.sleep(120000);
    }

    @After
    public void tearDown() {
        if (deviceOs.equalsIgnoreCase("android")) {
            System.out.println(EmptyTest.class.getName() + ": Report URL for device " + this.deviceSN + ": "+ androidDriver.getCapabilities().getCapability("reportUrl"));
            androidDriver.quit();
        }
        else {
            System.out.println(EmptyTest.class.getName() + ": Report URL for device " + this.deviceSN + ": "+ iosDriver.getCapabilities().getCapability("reportUrl"));
            iosDriver.quit();
        }

    }
}

