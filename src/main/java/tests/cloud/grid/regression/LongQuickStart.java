package tests.cloud.grid.regression;

import base.AutoProject;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LongQuickStart extends BaseTest {
    protected long TOTAL_TIME = 36000000; // = 10 hours
    protected Client client = null;
    protected GridClient grid = null;
    //    protected String query = "@emulator='true'";
    protected String query = "@serialNumber='" + this.deviceSN + "'";

    @Before
    public void setUp(){
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("LOOOONG RUN", query, 720, 60*60000*12);
        client.setReporter("xml", "", "LOOOONG RUN");
    }

    @Test
    public void quickStartAndroidNativeDemo(){
        client.install("cloud:com.experitest.ExperiBank", true, false);
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < TOTAL_TIME) {
            client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
            client.elementSendText("NATIVE", "hint=Username", 0, "company");
            client.elementSendText("NATIVE", "hint=Password", 0, "company");
            client.click("NATIVE", "text=Login", 0, 1);
            client.click("NATIVE", "text=Make Payment", 0, 1);
            client.elementSendText("NATIVE", "hint=Phone", 0, "1234567");
            client.elementSendText("NATIVE", "hint=Name", 0, "Jon Snow");
            client.elementSendText("NATIVE", "hint=Amount", 0, "50");
            client.click("NATIVE", "hint=Country", 0, 1);
            client.click("NATIVE", "text=Select", 0, 1);
            client.click("NATIVE", "text=Switzerland", 0, 1);
            client.click("NATIVE", "text=Send Payment", 0, 1);
            client.click("NATIVE", "text=Yes", 0, 1);
            client.click("NATIVE", "text=Logout", 0, 1);
            client.deviceAction("home");
        }
    }


    @After
    public void tearDown(){
        client.deviceAction("home");
        client.generateReport(false);
        client.releaseClient();
    }
}
