package tests.cloud.grid.issues;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class SA35116 extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("SA35116 Fingerprint investigation", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "SA35116 Fingerprint investigation");
    }

    @Test
    public void quickStartAndroidNativeDemo(){
        client.setProperty("android.instrument.fingerprint","true");
        client.install("C:\\Users\\michael.baran\\Desktop\\apps\\apk\\app-qa-legacyRdc-release.apk", true, false);
        client.launch("com.tbcu.mobile/com.orcc.MainActivity", true, true);
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
