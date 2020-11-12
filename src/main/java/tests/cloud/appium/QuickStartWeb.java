package tests.cloud.appium;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileBrowserType;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.BaseTest;

import java.net.MalformedURLException;
import java.net.URL;

public class QuickStartWeb extends BaseTest {

    protected AndroidDriver<AndroidElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();

    @Before
    public void setUp() throws MalformedURLException {
        dc.setCapability("testName", "Quick Start Android Browser Demo");
        dc.setCapability("accessKey", this.accessKey);
        dc.setCapability("deviceQuery", "@serialNumber='" + this.deviceSN + "'");
        dc.setBrowserName(MobileBrowserType.CHROMIUM);
        driver = new AndroidDriver<>(new URL(this.url + "/wd/hub"), dc);
    }

    @Test
    public void quickStartAndroidBrowserDemo() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.get("https://www.google.com");
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        WebElement searchBar = driver.findElement(By.name("q"));
        searchBar.sendKeys("Experitest");
        driver.findElement(By.xpath("//*[@css='BUTTON.Tg7LZd']")).click();
    }

    @After
    public void tearDown() {
        System.out.println("Report URL: "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }
}