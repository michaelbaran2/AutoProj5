package tests.cloud.grid;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

import java.io.File;

public class FingerprintAuthentication extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        grid = new GridClient(this.accessKey, this.url);
    }


    @Test
    public void symmetricAuthenticationSuccessTest() {
        client = grid.lockDeviceForExecution("Symmetric Authentication Test - Auth Succeed", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
//        client.startLoggingDevice("logs" + File.separator + this.deviceSN + "_symmetricAuthenticationSuccessTest.log");
        client.setReporter("xml", "", "Symmetric Authentication Test - Auth Succeed");
        client.setProperty("android.install.grant.permissions", "true");
        client.setProperty("android.instrument.fingerprint.secure", "true");
        client.install("resources\\apk\\UICatalog.apk", true, false);
//        client.install("cloud:com.experitest.uicatalog", true, false);
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("down", 100, 1000, "native", "xpath=//*[@text='Fingerprint Authentication']", 0, 1000, 5, true);
        client.sleep(3000);
        client.click("NATIVE", "xpath=//*[@text='Symmetric Authentication']", 0, 1); // //*[@text='SYMMETRIC AUTHENTICATION']
        client.setAuthenticationReply("AUTHENTICATION_SUCCEEDED", 10);
        client.click("NATIVE", "xpath=//*[@text='Start']", 0, 1);
        client.sleep(5000);
        client.setAuthenticationReply("CLEAR_MOCK", 0);
        Assert.assertTrue("Didn't find 'Fingerprint recognized' message on screen", client.isElementFound("NATIVE", "xpath=//*[@text='Fingerprint recognized']"));
    }

    @Test
    public void asymmetricAuthenticationSuccessTest() {
        client = grid.lockDeviceForExecution("Asymmetric Authentication Test - Auth Succeed", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
//        client.startLoggingDevice("logs" + File.separator + this.deviceSN + "_asymmetricAuthenticationSuccessTest.log");
        client.setReporter("xml", "", "Asymmetric Authentication Test - Auth Succeed");
        client.setProperty("android.install.grant.permissions", "true");
        client.setProperty("android.instrument.fingerprint.secure", "true");
        client.install("resources\\apk\\UICatalog.apk", true, false);
//        client.install("cloud:com.experitest.uicatalog", true, false);
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("down", 100, 1000, "native", "xpath=//*[@text='Fingerprint Authentication']", 0, 1000, 5, true);
        client.sleep(3000);
        client.click("NATIVE", "xpath=//*[@text='Asymmetric Authentication']", 0, 1);
        client.setAuthenticationReply("AUTHENTICATION_SUCCEEDED", 10);
        client.click("NATIVE", "xpath=//*[@text='Start']", 0, 1);
        client.sleep(5000);
        Assert.assertTrue("Didn't find 'Fingerprint recognized' message on screen", client.isElementFound("NATIVE", "xpath=//*[@text='Fingerprint recognized']")); // xpath=//*[@text='Symmetric Authentication']
    }

    @Test
    public void symmetricAuthenticationFailedTest() {
        client = grid.lockDeviceForExecution("Symmetric Authentication Test - Auth Failed", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
//        client.startLoggingDevice("logs" + File.separator + this.deviceSN + "_symmetricAuthenticationFailedTest.log");
        client.setReporter("xml", "", "Symmetric Authentication Test - Auth Failed");
        client.setProperty("android.install.grant.permissions", "true");
        client.setProperty("android.instrument.fingerprint.secure", "true");
        client.install("resources\\apk\\UICatalog.apk", true, false);
//        client.install("cloud:com.experitest.uicatalog", true, false);
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("down", 100, 1000, "native", "xpath=//*[@text='Fingerprint Authentication']", 0, 1000, 5, true);
        client.sleep(3000);
        client.click("NATIVE", "xpath=//*[@text='Symmetric Authentication']", 0, 1); // xpath=//*[@text='Symmetric Authentication']
        client.setAuthenticationReply("AUTHENTICATION_FAILED", 10);
        client.click("NATIVE", "xpath=//*[@text='Start']", 0, 1);
        client.sleep(5000);
        Assert.assertTrue("Didn't find 'Failed - Fingerprint not recognized. Try again' message on screen", client.isElementFound("NATIVE", "xpath=//*[@text='Failed - Fingerprint not recognized. Try again']"));
    }

    @Test
    public void asymmetricAuthenticationFailedTest() {
        client = grid.lockDeviceForExecution("Asymmetric Authentication Test - Auth Failed", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.startLoggingDevice("logs" + File.separator + this.deviceSN + "_asymmetricAuthenticationFailedTest.log");
        client.startLoggingDevice("logs" + File.separator + this.deviceSN + "-logcat.log");
        client.setReporter("xml", "", "Asymmetric Authentication Test - Auth Failed");
        client.setProperty("android.install.grant.permissions", "true");
        client.setProperty("android.instrument.fingerprint.secure", "true");
//        client.install("resources\\apk\\UICatalog.apk", true, false);
        client.install("cloud:com.experitest.uicatalog", true, false);
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("down", 100, 1000, "native", "xpath=//*[@text='Fingerprint Authentication']", 0, 1000, 5, true);
        client.sleep(3000);
        client.click("NATIVE", "xpath=//*[@text='Asymmetric Authentication']", 0 ,1);
        client.setAuthenticationReply("AUTHENTICATION_FAILED", 10);
        client.click("NATIVE", "xpath=//*[@text='Start']", 0, 1);
        client.sleep(5000);
        Assert.assertTrue("Didn't find 'Failed - Fingerprint not recognized. Try again' message on screen", client.isElementFound("NATIVE", "xpath=//*[@text='Failed - Fingerprint not recognized. Try again']"));
    }

    @After
    public void tearDown(){
        client.deviceAction("home");
        client.generateReport(false);
        client.releaseClient();
    }

}
