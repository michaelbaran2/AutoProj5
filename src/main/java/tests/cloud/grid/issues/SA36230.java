package tests.cloud.grid.issues;
import com.experitest.client.*;
import org.junit.*;
import tests.BaseTest;

public class SA36230 extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;
    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("SA36230", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "SA36230");

    }

    @Test
    public void installAndLaunch(){
        // This command "setDevice" is not applicable for GRID execution
        client.install("cloud:com.gm.gfs.vehicleinsights", true, false);
        client.launch("com.gm.gfs.vehicleinsights/.MainActivity", true, true);
        client.click("native", "//*[@text='ALLOW']", 0, 1);
        client.sleep(180000);
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
