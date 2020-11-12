package tests.cloud.grid;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class LaunchChrome extends BaseTest {
    protected long TOTAL_TIME = 36000000; // = 10 hours
    protected Client client = null;
    protected GridClient grid = null;
    protected String query = "@emulator='true'";
//    protected String query = "@serialNumber='" + this.deviceSN + "'";

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("Launch Chrome test", query, 720, 60*60000*12);
        client.setReporter("xml", "", "Launch Chrome test");
    }

    @Test
    public void launchChrome(){
        String[] urls = { "www.wikipedia.com", "developer.android.com/guide", "www.google.com", "www.youtube.com", "www.spotify.com"};
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < TOTAL_TIME) {
            for (String url: urls) {
                client.launch("chrome:" + url, false, true);
                client.sleep(10000);
            }
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
