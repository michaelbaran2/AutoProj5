package tests.local.sta;

import com.experitest.client.Client;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class Minicap extends BaseTest {
    private String host = "localhost";
    private int port = 8889;
    private String projectBaseDirectory = "C:\\Users\\michael.baran\\workspace\\project4";
    protected Client client = null;
    private String[] modes = {"Portrait", "Landscape"};
    private String deviceName = "adb:A0001";


    @Before
    public void setUp(){
        client = new Client(host, port, true);
        client.setProjectBaseDirectory(projectBaseDirectory);
        client.setReporter("xml", "reports", "generic");
//        client.setProperty("default.android.screenshooter", "minicap");
    }

//    @Test
//    public void OrientationSwitchTest(){
//        client.setDevice(deviceName);
//        client.launch("chrome:www.wikipedia.org", false, false);
//        client.sleep(5000);
//        for (int i = 0; i < 700; i++) {
//            client.deviceAction(modes[i % 2]);
//            client.sleep(1000);
//        }
//    }

//    @Test
//    public void clicksTest() {
//        client.setDevice(deviceName);
//        client.install("com.experitest.ExperiBank/.LoginActivity", false, true);
//        for (int i = 0; i < 700; i++) {
//            client.deviceAction(modes[i % 2]);
//            client.launch("com.experitest.ExperiBank/.LoginActivity", false, true);
//            client.sleep(1000);
//            client.elementSendText("NATIVE", "xpath=//*[@id='usernameTextField']", 0, "company");
//            client.elementSendText("NATIVE", "xpath=//*[@id='passwordTextField']", 0, "company");
//            client.click("NATIVE", "xpath=//*[@text='Login']", 0, 1);
//            Assert.assertTrue("Expected to find 'make payment' button on the screen", client.isElementFound("NATIVE", "xpath=//*[@text='Make Payment']"));
//        }
//    }

    @Test
    public void clickTest2() {
        client.setDevice(deviceName);
        client.install("com.experitest.ExperiBank/.LoginActivity", false, true);
        for (int i = 0; i < 700; i++) {
            client.deviceAction(modes[i % 2]);
            client.launch("com.experitest.ExperiBank/.LoginActivity", false, true);
            client.sleep(1000);
            client.elementSendText("NATIVE", "xpath=//*[@id='usernameTextField']", 0, "company");
            client.elementSendText("NATIVE", "xpath=//*[@id='passwordTextField']", 0, "company");
            client.click("NATIVE", "xpath=//*[@text='Login']", 0, 1);
            Assert.assertTrue("Expected to find 'make payment' button on the screen", client.isElementFound("NATIVE", "xpath=//*[@text='Make Payment']"));
            client.click("NATIVE", "xpath=//*[@text='Mortgage Request']", 0, 1);
            Assert.assertTrue("Expected to find 'make payment' button on the screen", client.isElementFound("NATIVE", "xpath=//*[@id='addressOneTextField']"));
        }
    }

    @After
    public void tearDown(){
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        client.generateReport(false);
        // Releases the client so that other clients can approach the agent in the near future.
        client.releaseClient();
    }

}
