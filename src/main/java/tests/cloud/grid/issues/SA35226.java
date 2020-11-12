package tests.cloud.grid.issues;

import base.AutoProject;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class SA35226 extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("changeOrientationAndClickTest", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "changeOrientationAndClickTest");
    }

    @Test
    public void changeOrientationAndClickTest(){
        client.install("cloud:com.experitest.ExperiBank", true, false);
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
        client.generateReport(false);
        client.releaseClient();
    }
}
