package tests.cloud.grid;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class SimpleInstall extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("Basic Install", "@serialNumber='" + this.deviceSN + "'", 720, 60*60000*12);
        client.setReporter("xml", "", "Basic Install");
    }

    @Test
    public void basicInstall(){
        String apkToInstall = "cloud:eu.tsystems.mms.tic.mdc.app.android/.HomeActivity";
        client.install(apkToInstall, false, false);
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
