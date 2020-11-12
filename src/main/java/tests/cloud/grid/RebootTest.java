package tests.cloud.grid;
import com.experitest.client.*;
import org.junit.*;
import tests.BaseTest;

public class RebootTest extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;
    protected int iterations = 4;

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("Whole Lotta Reboots", "@serialNumber='" + this.deviceSN + "'", 720, 60*60000*12);
        client.setReporter("xml", "", "Whole Lotta Reboots");

    }

    @Test
    public void quickStartInstallFromCloud(){
        for (int i = 0; i < iterations; ++i) {
            client.reboot(300000);
            client.deviceAction("unlock");
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
