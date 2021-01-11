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
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import tests.BaseTest;
import tests.cloud.appium.QuickStart;

import java.net.MalformedURLException;
import java.net.URL;

public class InstallInstrumentedTest extends BaseTest {
    protected AndroidDriver<AndroidElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();

    @Before
    public void setUp() throws MalformedURLException {
        dc.setCapability("testName", "Install Instrumented Sanity Test");
        dc.setCapability("accessKey", this.accessKey);
        dc.setCapability("deviceQuery", "@serialNumber='" + this.deviceSN + "'");
        dc.setCapability("autoGrantPermissions", true);
        dc.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);
        dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.uicatalog/.MainActivity");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.uicatalog");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
        driver = new AndroidDriver<>(new URL(this.url + "/wd/hub"), dc);

    }

    @Test
    public void installFromCloud() {
        String xpath = "(//*[@id='listView']/*[@id='text1'])[%d]"; // instrmented - //*[@text='Capture']
                                                                    // non-instrumented - //*[@text='CAPTURE']
        driver.rotate(ScreenOrientation.PORTRAIT);
        if(!driver.findElement(By.xpath("//*[@text='Camera']")).isDisplayed()) {
            int i = 3;
            while(driver.findElement(By.xpath(String.format(xpath, i))).isDisplayed()) {
                i++;
            }
            String middleXpath = String.format(xpath, (i + 1) / 2);
            while(!driver.findElement(By.xpath("//*[@text='Camera']")).isDisplayed()) {
                TouchActions action = new TouchActions(driver);
                action.scroll(driver.findElement(By.xpath(middleXpath)), 0, 100);
                action.perform();
            }
        }
        driver.findElement(By.xpath("//*[@text='Camera']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//*[@text='Camera api1']")).isDisplayed());
        driver.findElement(By.xpath("//*[@text='Camera api1']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//*[@text='Capture']")).isDisplayed());

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
        System.out.println(InstallInstrumentedTest.class.getName() + ": Report URL for device " + this.deviceSN + ": "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }
}
