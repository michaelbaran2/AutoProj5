package tests.local.ase;

import com.experitest.appium.SeeTestCapabilityType;
import com.experitest.appium.SeeTestClient;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import tests.BaseTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;


public class FingerprintTest extends BaseTest {
    protected AndroidDriver<AndroidElement> driver = null;
    protected SeeTestClient client;
    protected DesiredCapabilities dc = new DesiredCapabilities();


    @Before
    public void setUp() throws MalformedURLException {
        dc.setCapability("testName", "ASE fingerprint authentication test");
        dc.setCapability(MobileCapabilityType.UDID, this.deviceSN);
        dc.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);
        driver = new AndroidDriver<>(new URL(this.url + "/wd/hub"), dc);
        driver.setLogLevel(Level.INFO);
        client = new SeeTestClient(driver);
        client.setProperty("android.install.grant.permissions", "true");
        client.setProperty("android.instrument.fingerprint.secure", "true");
        client.install("C:\\Users\\michael.baran\\IdeaProjects\\autoProj5\\resources\\apk\\UICatalog.apk", true, false);

    }

    @Test
    public void symmetricAuthenticationTest() {
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("down", 100, 1000, "native", "//*[@text='Fingerprint Authentication']", 0, 1000, 5, true);
        driver.findElement(By.xpath("//*[@text='Symmetric Authentication']")).click();
        client.setAuthenticationReply("AUTHENTICATION_SUCCEEDED", 10);
        driver.findElement(By.xpath("//*[@text='Start']")).click();
        client.sleep(5000);
        Assert.assertTrue("Didn't find 'Fingerprint recognized' message on screen", client.isElementFound("NATIVE", "xpath=//*[@text='Fingerprint recognized']"));
        client.setAuthenticationReply("CLEAR_MOCK", 0);
    }

    @Test
    public void asymmetricAuthenticationTest() {
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("down", 100, 1000, "native", "//*[@text='Fingerprint Authentication']", 0, 1000, 5, true);
        driver.findElement(By.xpath("//*[@text='Asymmetric Authentication']")).click();
        client.setAuthenticationReply("AUTHENTICATION_SUCCEEDED", 10);
        driver.findElement(By.xpath("//*[@text='Start']")).click();
        client.sleep(5000);
        Assert.assertTrue("Didn't find 'Fingerprint recognized' message on screen", client.isElementFound("NATIVE", "xpath=//*[@text='Fingerprint recognized']"));
        client.setAuthenticationReply("CLEAR_MOCK", 0);
    }

    @Test
    public void asymmetricAuthenticationFailedTest() {
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("down", 100, 1000, "native", "//*[@text='Fingerprint Authentication']", 0, 1000, 5, true);
        driver.findElement(By.xpath("//*[@text='Asymmetric Authentication']")).click();
        client.setAuthenticationReply("AUTHENTICATION_FAILED", 10);
        driver.findElement(By.xpath("//*[@text='Start']")).click();
        client.sleep(5000);
        Assert.assertTrue("Didn't find 'Failed - Fingerprint not recognized. Try again' message on screen", client.isElementFound("NATIVE", "xpath=//*[@text='Failed - Fingerprint not recognized. Try again']"));
        client.setAuthenticationReply("CLEAR_MOCK", 0);
    }

    @Test
    public void symmetricAuthenticationFailedTest() {
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("down", 100, 1000, "native", "//*[@text='Fingerprint Authentication']", 0, 1000, 5, true);
        driver.findElement(By.xpath("//*[@text='Asymmetric Authentication']")).click();
        client.setAuthenticationReply("AUTHENTICATION_FAILED", 10);
        driver.findElement(By.xpath("//*[@text='Start']")).click();
        client.sleep(5000);
        Assert.assertTrue("Didn't find 'Failed - Fingerprint not recognized. Try again' message on screen", client.isElementFound("NATIVE", "xpath=//*[@text='Failed - Fingerprint not recognized. Try again']"));
        client.setAuthenticationReply("CLEAR_MOCK", 0);
    }

    @After
    public void tearDown() {
        client.deviceAction("home");
        driver.quit();
    }
}
