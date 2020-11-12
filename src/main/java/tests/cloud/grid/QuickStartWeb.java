package tests.cloud.grid;




import com.experitest.client.*;
import org.junit.*;
import tests.BaseTest;

/**
 *
 */
public class QuickStartWeb extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("Quick Start seetest Android Web Demo", "@serialNumber='" + this.deviceSN + "'", 720, 60*60000*12);
        client.setReporter("xml", "", "Quick Start seetest Android Web Demo");

    }

    @Test
    public void quickStartAndroidWebDemo(){
        client.hybridClearCache(true, true);
        client.launch("chrome:http://google.com", true, false);
        if(client.waitForElement("WEB", "name=q", 0, 60000)){
            // If statement
        }
        client.elementSendText("WEB", "name=q", 0, "experitest");
        client.click("WEB", "name=q", 0, 1);
        client.sendText("{Enter}");
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


