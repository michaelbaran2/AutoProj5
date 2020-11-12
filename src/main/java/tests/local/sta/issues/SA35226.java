package tests.local.sta.issues;

import base.AutoProject;
import com.experitest.client.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SA35226 {
    protected Client client = null;
    protected String host = "localhost";
    protected int port = 8889;
    protected String deviceName = "adb:SM-G970F";
    protected static final String  ERIBANK_ABS_PATH = "C:\\Users\\michael.baran\\IdeaProjects\\autoProj5\\resources\\apk\\eribank.apk";

    @Before
    public void setUp(){
        client = new Client(host, port, true);
    }


    @Test
    public void changeOrientationAndClickTest() {
        client.setDevice(deviceName);
        client.install(ERIBANK_ABS_PATH, true, false);
        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        client.elementSendText("NATIVE", "hint=Username", 0, "company");
        client.elementSendText("NATIVE", "hint=Password", 0, "company");
        client.click("NATIVE", "text=Login", 0, 1);
        for (int i = 0; i < 30; i++) {
            client.deviceAction("Landscape");
            client.click("NATIVE", "text=Make Payment", 0, 1);
            client.deviceAction("Portrait");
            client.click("NATIVE", "xpath=//*[@text='Cancel']", 0, 1);
            AutoProject.LOGGER.info("Iteration: " + (i + 1));
        }
    }

    @After
    public void tearDown(){
        client.deviceAction("home");
        client.generateReport(false);
        client.releaseClient();
    }
}
