package tests.cloud.appium.sanity;

import com.experitest.appium.SeeTestCapabilityType;
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
import tests.cloud.appium.QuickStart;

import java.net.MalformedURLException;
import java.net.URL;

public class InstallNonInstrumentedTest extends BaseTest {
    protected AndroidDriver<AndroidElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();

    @Before
    public void setUp() throws MalformedURLException {
        dc.setCapability("testName", "Install Non Instrumented Sanity Test");
        dc.setCapability("accessKey", this.accessKey);
        dc.setCapability("deviceQuery", "@serialNumber='" + this.deviceSN + "'");
        dc.setCapability("autoGrantPermissions", true);
        dc.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, false);
        dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.uicatalog/.MainActivity");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.uicatalog");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
        driver = new AndroidDriver<>(new URL(this.url + "/wd/hub"), dc);
    }

    @Test
    public void installFromCloud() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("+ "new UiSelector().text(\"Camera\"));");
        driver.findElement(By.xpath("//*[@text='Camera']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//*[@text='Camera api1']")).isDisplayed());
    }

//    @Test
//    public void installFromPath() throws MalformedURLException {
//        dc.setCapability(MobileCapabilityType.APP, UICATALOG_ABS_PATH);
//        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.uicatalog");
//        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
//        driver = new AndroidDriver<>(new URL(this.url + "/wd/hub"), dc);
//        driver.findElement(By.xpath("//*[@text='Camera']")).click();
//        Assert.assertTrue(driver.findElement(By.xpath("//*[@text='Camera api1']")).isDisplayed());
//    }


    @After
    public void tearDown() {
        System.out.println(InstallNonInstrumentedTest.class.getName() + ": Report URL for device " + this.deviceSN + ": "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }
}
