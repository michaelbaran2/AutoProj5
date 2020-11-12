package tests.cloud.appium.issues;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;


import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import tests.BaseTest;

public class SA37400 {


    protected AndroidDriver<AndroidElement> driver = null;
    protected String deviceSN = "ce061716ba5f3cdd0d7e";
    DesiredCapabilities dc = new DesiredCapabilities();

    @Before
    public void setUp() throws MalformedURLException {
        System.getProperty("file.encoding","UTF-8");
        dc.setCapability("testName", "SA34700");
//        dc.setCapability("accessKey", this.accessKey);
        dc.setCapability("accessKey", "eyJ4cC51Ijo3ODkzNDY1LCJ4cC5wIjozLCJ4cC5tIjoiTVRVNE1ERXlOall3TURBek13IiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4OTU0ODY2MDAsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.cxbB0wglzNvMvnBW_t_lBWrkpVyp-gtFgTSPE_5p69A");
//        dc.setCapability("deviceQuery", "@os='android' and @category='PHONE' and @serialNumber='" + this.deviceSN + "'");
        dc.setCapability("deviceQuery", "@os='android' and @category='PHONE' and @serialNumber='" + deviceSN + "'");
        dc.setCapability("install.only.for.update", true);
        dc.setCapability("noReset", false);
        dc.setCapability("appBuildVersion", "588");
        dc.setCapability(MobileCapabilityType.APP, "cloud:uniqueName=5544332211");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.arneca.dergilik.main3x");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.solidict.dergilik.activities.SplashActivity");
//        driver = new AndroidDriver<>(new URL(this.url + "/wd/hub"), dc);
        driver = new AndroidDriver<>(new URL("https://cloud.seetest.io/wd/hub"), dc);
        //driver.executeScript("seetest:client.startPerformanceTransaction(\"4G-average\")");
    }



    @Test
    public void sliderProblem() throws InterruptedException {
        try {
            driver.findElement(By.xpath("//*[@id='permission_allow_button']")).click();
        }catch(Exception e) {
            System.out.println("No allow button found");
        }
        driver.executeScript("seetest:client.waitForElement('NATIVE', 'id=iv_esc', 0, 30000)");
//        Thread.sleep(30000);
        driver.findElement(By.id("iv_esc")).click();
        driver.findElement(By.id("iv_icon_ustbar_search")).click();
        MobileElement elem= (MobileElement) driver.findElement(By.id("et_search"));
        String value = "yııldıızıın";
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        String utf8 = new String(bytes, StandardCharsets.UTF_8);

        elem.click();
        Thread.sleep(8000);
//        driver.getKeyboard().sendKeys(value);
        elem.sendKeys(utf8);
    }

    @After
    public void tearDown() {
        System.out.println("Report URL: "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }
}