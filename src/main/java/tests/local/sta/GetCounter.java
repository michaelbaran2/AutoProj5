package tests.local.sta;

import base.AutoProject;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class GetCounter {
    private String host = "localhost";
    private int port = 8889;
    private String projectBaseDirectory = "C:\\Users\\MichaelBaran\\workspace\\project2";
    protected Client client = null;


    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        client = new Client(host, port, true);
        client.setProjectBaseDirectory(projectBaseDirectory);
        client.setReporter("xml", "reports", "script1");
        client.setReporter("xml", "", "SeeTest Quick Start Test");
        client.setDevice("adb:Pixel 5");
//        client.setDevice("adb:Google Pixel 3a");
        client.uninstall("com.experitest.ExperiBank");
    }

    @Test
    public void getCounter() throws InterruptedException {
//        client.startLoggingDevice("longrunoutput" + File.separator + this.deviceSN + "-logcat.log");
        client.setProperty("android.install.grant.permissions", "true");
        client.install("com.experitest.ExperiBank/.LoginActivity", true, false);
        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        for (int i = 0; i < 10; i++) {
            Double memory = Double.parseDouble(client.getCounter("memory:com.experitest.ExperiBank"));
            boolean greaterThenZero = Double.compare(memory, 0.0d) > 0;
            if (!greaterThenZero) {
                Thread.sleep(1000);
            }
            AutoProject.LOGGER.info(">>> Memory " + memory);
//            Assert.assertTrue("Memory value shouldn't be 0 or negative", greaterThenZero);
        }
        client.stopLoggingDevice();
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
