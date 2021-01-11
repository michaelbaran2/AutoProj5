package tests.cloud.grid;

import base.AutoProject;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tests.BaseTest;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class SetMonitorPollingInterval extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Parameterized.Parameters
    public static Iterable<? extends Object> data() {
        return Arrays.asList(500, 1000, 3000, 5000);
    }

    @Parameterized.Parameter
    public int interval;


    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("SeeTest Quick Start Test", "@serialNumber='" + this.deviceSN + "'", 720, 60*60000*12);
        client.setReporter("xml", "", "SeeTest Quick Start Test");
        client.uninstall("cloud:com.experitest.ExperiBank");
    }

    @Test
    public void monitorPolling() throws InterruptedException {
        client.setProperty("android.install.grant.permissions", "true");
        client.install("cloud:com.experitest.ExperiBank", true, false);
        // spamInstall(client);
        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        client.setMonitorPollingInterval(interval);
//        long start = System.currentTimeMillis();
        client.startMonitor("com.experitest.ExperiBank");
        Thread.sleep(30000);
//        Thread.sleep(3000);
//        client.elementSendText("NATIVE", "hint=Username", 0, "company");
//        client.elementSendText("NATIVE", "hint=Password", 0, "company");
//        client.click("NATIVE", "text=Login", 0, 1);
//        Thread.sleep(3000);
//        client.click("NATIVE", "text=Make Payment", 0, 1);
//        client.elementSendText("NATIVE", "hint=Phone", 0, "1234567");
//        client.elementSendText("NATIVE", "hint=Name", 0, "Jon Snow");
//        client.elementSendText("NATIVE", "hint=Amount", 0, "50");
//        client.click("NATIVE", "hint=Country", 0, 1);
//        client.click("NATIVE", "text=Select", 0, 1);
//        client.click("NATIVE", "text=Switzerland", 0, 1);
//        client.click("NATIVE", "text=Send Payment", 0, 1);
//        client.click("NATIVE", "text=Yes", 0, 1);
//        Thread.sleep(3000);
//        client.click("NATIVE", "text=Make Payment", 0, 1);
//        client.elementSendText("NATIVE", "hint=Phone", 0, "1234567");
//        client.elementSendText("NATIVE", "hint=Name", 0, "Jon Snow");
//        client.elementSendText("NATIVE", "hint=Amount", 0, "50");
//        client.click("NATIVE", "hint=Country", 0, 1);
//        client.click("NATIVE", "text=Select", 0, 1);
//        client.click("NATIVE", "text=Switzerland", 0, 1);
//        client.click("NATIVE", "text=Send Payment", 0, 1);
//        client.click("NATIVE", "text=Yes", 0, 1);
//        Thread.sleep(3000);
//        client.click("NATIVE", "text=Logout", 0, 1);
        String data = client.getMonitorsData();
//        long end = System.currentTimeMillis();
        Assert.assertNotNull(data);
        int expectedEntries =30000 / interval;
//        int expectedEntries =(int)((end - start) / interval);
        int actualEntries = data.split("\n").length - 2;
        AutoProject.LOGGER.info(this.deviceSN + " Monitors Data:\n" + data + "\nExpected number of entries: " + expectedEntries + "\nActual number of entries: " + actualEntries);
        System.out.println("@@@ >>>> " + this.deviceSN + " interval = " + interval + ", Expected: " + expectedEntries + ", actual: " + actualEntries);

    }


    @After
    public void tearDown(){
        client.deviceAction("home");
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        client.generateReport(false);
        // Releases the client so that other clients can approach the agent in the near future.
        client.releaseClient();
    }
}
