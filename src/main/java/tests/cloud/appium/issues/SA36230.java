package tests.cloud.appium.issues;

import com.experitest.appium.SeeTestCapabilityType;
import com.experitest.appium.SeeTestClient;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import tests.BaseTest;

import java.net.MalformedURLException;
import java.net.URL;

public class SA36230 extends BaseTest {
    protected AndroidDriver<AndroidElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();
    protected SeeTestClient client = null;

    @Before
    public void setUp() throws MalformedURLException {
        dc.setCapability("testName", "ASE launch GM app");
        dc.setCapability("accessKey", this.accessKey);
        dc.setCapability("deviceQuery", "@serialNumber='" + this.deviceSN + "'");

        dc.setCapability(MobileCapabilityType.APP, "cloud:com.gm.gfs.vehicleinsights/.MainActivity");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.gm.gfs.vehicleinsights");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
//        dc.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);

        driver = new AndroidDriver<>(new URL(this.url + "/wd/hub"), dc);
        client = new SeeTestClient(driver);
    }

    @Test
    public void quickStartAndroidNativeDemo() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        client.install("cloud:com.gm.gfs.vehicleinsights", true, false);
        client.launch("com.gm.gfs.vehicleinsights/.MainActivity", true, true);
//        driver.findElement(By.xpath("//*[@text='ALLOW']")).click();
        client.sleep(180000);
    }



    @After
    public void tearDown() {
        System.out.println("Report URL: "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }

}
