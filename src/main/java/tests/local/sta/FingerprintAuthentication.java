package tests.local.sta;

import com.experitest.client.Client;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;


public class FingerprintAuthentication {
    protected Client client = null;
//    protected GridClient grid = null;
    protected String host = "localhost";
    protected int port = 8889;
    protected String deviceName = "adb:SM-G980F";
    protected static final String  UICATALOG_ABS_PATH = "C:\\Users\\michael.baran\\IdeaProjects\\autoProj5\\resources\\apk\\UICatalog.apk";

    @Before
    public void setUp(){
//        grid = new GridClient(this.accessKey, this.url);
        client = new Client(host, port, true);
    }


    @Test
    public void symmetricAuthenticationSuccessTest() {
//        client = grid.lockDeviceForExecution("Symmetric Authentication Test - Auth Succeed", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setDevice(deviceName);
        client.setReporter("xml", "", "Symmetric Authentication Test - Auth Succeed");
        client.setProperty("android.install.grant.permissions", "true");
        client.setProperty("android.instrument.fingerprint.secure", "true");
        client.install(UICATALOG_ABS_PATH, true, false);
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("down", 100, 1000, "native", "xpath=//*[@text='Fingerprint Authentication']", 0, 1000, 5, true);
        client.sleep(3000);
        client.click("NATIVE", "xpath=//*[@text='Symmetric Authentication']", 0, 1); // //*[@text='SYMMETRIC AUTHENTICATION']
        client.setAuthenticationReply("AUTHENTICATION_SUCCEEDED", 10);
        client.click("NATIVE", "xpath=//*[@text='Start']", 0, 1);
        client.sleep(5000);
        Assert.assertTrue("Didn't find 'Fingerprint recognized' message on screen", client.isElementFound("NATIVE", "xpath=//*[@text='Fingerprint recognized']"));
        client.setAuthenticationReply("CLEAR_MOCK", 0);
    }

    @Test
    public void asymmetricAuthenticationSuccessTest() {
//        client = grid.lockDeviceForExecution("Asymmetric Authentication Test - Auth Succeed", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setDevice(deviceName);
        client.setReporter("xml", "", "Asymmetric Authentication Test - Auth Succeed");
        client.setProperty("android.install.grant.permissions", "true");
        client.setProperty("android.instrument.fingerprint.secure", "true");
        client.install(UICATALOG_ABS_PATH, true, false);
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("down", 100, 1000, "native", "xpath=//*[@text='Fingerprint Authentication']", 0, 1000, 5, true);
        client.sleep(3000);
        client.click("NATIVE", "xpath=//*[@text='Asymmetric Authentication']", 0, 1);
        client.setAuthenticationReply("AUTHENTICATION_SUCCEEDED", 10);
        client.click("NATIVE", "xpath=//*[@text='Start']", 0, 1);
        client.sleep(5000);
        Assert.assertTrue("Didn't find 'Fingerprint recognized' message on screen", client.isElementFound("NATIVE", "xpath=//*[@text='Fingerprint recognized']")); // xpath=//*[@text='Symmetric Authentication']
        client.setAuthenticationReply("CLEAR_MOCK", 0);
    }

    @Test
    public void symmetricAuthenticationFailedTest() {
//        client = grid.lockDeviceForExecution("Symmetric Authentication Test - Auth Failed", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setDevice(deviceName);
        client.setReporter("xml", "", "Symmetric Authentication Test - Auth Failed");
        client.setProperty("android.install.grant.permissions", "true");
        client.setProperty("android.instrument.fingerprint.secure", "true");
        client.install(UICATALOG_ABS_PATH, true, false);
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("down", 100, 1000, "native", "xpath=//*[@text='Fingerprint Authentication']", 0, 1000, 5, true);
        client.sleep(3000);
        client.click("NATIVE", "xpath=//*[@text='Symmetric Authentication']", 0, 1); // xpath=//*[@text='Symmetric Authentication']
        client.setAuthenticationReply("AUTHENTICATION_FAILED", 10);
        client.click("NATIVE", "xpath=//*[@text='Start']", 0, 1);
        client.sleep(5000);
        Assert.assertTrue("Didn't find 'Failed - Fingerprint not recognized. Try again' message on screen", client.isElementFound("NATIVE", "xpath=//*[@text='Failed - Fingerprint not recognized. Try again']"));
        client.setAuthenticationReply("CLEAR_MOCK", 0);
    }

    @Test
    public void asymmetricAuthenticationFailedTest() {
//        client = grid.lockDeviceForExecution("Asymmetric Authentication Test - Auth Failed", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setDevice(deviceName);
        client.setReporter("xml", "", "Asymmetric Authentication Test - Auth Failed");
        client.setProperty("android.install.grant.permissions", "true");
        client.setProperty("android.instrument.fingerprint.secure", "true");
        client.install(UICATALOG_ABS_PATH, true, false);
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("down", 100, 1000, "native", "xpath=//*[@text='Fingerprint Authentication']", 0, 1000, 5, true);
        client.sleep(3000);
        client.click("NATIVE", "xpath=//*[@text='Asymmetric Authentication']", 0 ,1);
        client.setAuthenticationReply("AUTHENTICATION_FAILED", 10);
        client.click("NATIVE", "xpath=//*[@text='Start']", 0, 1);
        client.sleep(5000);
        Assert.assertTrue("Didn't find 'Failed - Fingerprint not recognized. Try again' message on screen", client.isElementFound("NATIVE", "xpath=//*[@text='Failed - Fingerprint not recognized. Try again']"));
        client.setAuthenticationReply("CLEAR_MOCK", 0);
    }

    @After
    public void tearDown(){
        client.deviceAction("home");
        client.generateReport(false);
        client.releaseClient();
    }

}

