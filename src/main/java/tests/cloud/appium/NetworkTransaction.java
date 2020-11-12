package tests.cloud.appium;

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

public class NetworkTransaction extends BaseTest {
    protected AndroidDriver<AndroidElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();
    SeeTestClient seetest;

    @Before
    public void setUp() throws MalformedURLException {
        dc.setCapability("testName", "Network Transaction");
        dc.setCapability("accessKey", this.accessKey);
        dc.setCapability("deviceQuery", "@serialNumber='" + this.deviceSN + "'");
        dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
        driver = new AndroidDriver<>(new URL(this.url + "/wd/hub"), dc);
        seetest = new SeeTestClient(driver);
        dc.setCapability(MobileCapabilityType.UDID, "deviceid");
    }

    @Test
    public void networkTransactionDemoTest() {
        seetest.startPerformanceTransactionForApplication("com.experitest.ExperiBank", "4G-average");
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.findElement(By.xpath("//*[@id='usernameTextField']")).sendKeys("company");
        driver.hideKeyboard();
        driver.findElement(By.xpath("//*[@id='passwordTextField']")).sendKeys("company");
        driver.findElement(By.xpath("//*[@id='loginButton']")).click();
        String loginPerfData = seetest.endPerformanceTransaction("Login");
        System.out.println(loginPerfData);
    }

    @After
    public void tearDown() {
        System.out.println(NetworkTransaction.class.getName() + ": Report URL for device " + this.deviceSN + ": "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }
}
