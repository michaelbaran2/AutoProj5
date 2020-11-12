package tests.cloud.grid.issues;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class SA34408 extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("SA34408", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "SA34408");
    }

    @Test
    public void clickOnElementOutOfView(){
        client.install("cloud:com.experitest.uicatalog/.MainActivity", false, false);
        client.launch("com.experitest.uicatalog/.MainActivity", false, true);
        while (!client.isElementFound("NATIVE", "xpath=//*[@text='ScrollViews']")) { //
            client.sleep(10000);
        }
        Assert.assertTrue("Couldn't find scrollviews", client.isElementFound("NATIVE", "xpath=//*[@text='ScrollViews']"));
        client.deviceAction("home");
    }

    @After
    public void tearDown(){
        client.generateReport(false);
        client.releaseClient();
    }
}
