package tests.local.sta;

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
public class SetMonitorPollingInterval {
    private String host = "localhost";
    private int port = 8889;
    private String projectBaseDirectory = "C:\\Users\\MichaelBaran\\workspace\\project2";
    protected Client client = null;


    @Parameterized.Parameters
    public static Iterable<? extends Object> data() {
        return Arrays.asList(500, 1000, 3000, 5000);
    }

    @Parameterized.Parameter
    public int interval;


    @Before
    public void setUp(){
        client = new Client(host, port, true);
        client.setProjectBaseDirectory(projectBaseDirectory);
        client.setReporter("xml", "reports", "script1");

    }

    @Test
    public void monitorPolling() throws InterruptedException {
        client.setDevice("adb:Pixel 5");
        client.setProperty("android.install.grant.permissions", "true");
        client.install("com.experitest.ExperiBank/.LoginActivity", true, false);
        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        client.setMonitorPollingInterval(interval);
        client.startMonitor("com.experitest.ExperiBank");
        Thread.sleep(30000);
        String data = client.getMonitorsData();
        Assert.assertNotNull(data);
        int expectedEntries = 30000 / interval;
        int actualEntries = data.split("\n").length - 2;
        AutoProject.LOGGER.info("Monitors Data:\n" + data + "\nExpected number of entries: " + expectedEntries + "\nActual number of entries: " + actualEntries);
        System.out.println("@@@ >>>> interval = " + interval + ", Expected: " + expectedEntries + ", actual: " + actualEntries);

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
