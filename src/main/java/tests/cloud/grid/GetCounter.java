package tests.cloud.grid;
import base.AutoProject;
import com.experitest.client.*;
import org.junit.*;
import tests.BaseTest;

import java.io.File;

public class GetCounter extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

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
    public void quickStartInstallFromCloud() throws InterruptedException {
        client.startLoggingDevice("longrunoutput" + File.separator + this.deviceSN + "-logcat.log");
        client.setProperty("android.install.grant.permissions", "true");
        client.install("cloud:com.experitest.ExperiBank", true, false);
        spamInstall(client);
        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        for (int i = 0; i < 10; i++) {
            Double memory = Double.parseDouble(client.getCounter("memory:com.experitest.ExperiBank"));
            boolean greaterThenZero = Double.compare(memory, 0.0d) > 0;
            if (!greaterThenZero) {
                Thread.sleep(1000);
            }
            AutoProject.LOGGER.info(this.deviceSN + " >>> Memory " + memory);
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
