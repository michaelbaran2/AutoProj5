package tests.cloud.grid;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class SimpleInstall extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    protected String apkToInstall = UICATALOG_ABS_PATH;
    protected String activityUrl = "com.experitest.uicatalog/.MainActivity";
    protected String packageName = "com.experitest.uicatalog";

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("Basic Install", "@serialNumber='" + this.deviceSN + "'", 720, 60*60000*12);
        client.setReporter("xml", "", "Basic Install");
        client.uninstall(packageName);
        client.setProperty("android.install.grant.permissions", "true");
    }

    @Test
    public void basicInstallInstrumented(){
        client.install(apkToInstall, true, false);
        client.launch(activityUrl, true, true);
        client.swipeWhileNotFound("Down", 200, 100, "NATIVE", "xpath=//*[@text='Camera']", 0, 1000, 5, true);
        Assert.assertTrue(client.isElementFound("NATIVE", "xpath=//*[@text='Camera api1']"));
    }

    @Test
    public void basicInstallNonInstrumented(){
        client.install(apkToInstall, false, false);
        client.launch(activityUrl, false, true);
        client.swipeWhileNotFound("Down", 200, 100, "NATIVE", "xpath=//*[@text='Camera']", 0, 1000, 5, true);
        Assert.assertTrue(client.isElementFound("NATIVE", "xpath=//*[@text='Camera api1']"));
    }

    @Test
    public void basicCloudInstallInstrumented(){
        client.install("cloud:" + packageName, true, false);
        client.launch(activityUrl, true, true);
        client.swipeWhileNotFound("Down", 200, 100, "NATIVE", "xpath=//*[@text='Camera']", 0, 1000, 5, true);
        Assert.assertTrue(client.isElementFound("NATIVE", "xpath=//*[@text='Camera api1']"));
    }

    @Test
    public void basicCloudInstallNonInstrumented(){
        client.install("cloud:" + packageName, true, false);
        client.launch(activityUrl, false, true);
        client.swipeWhileNotFound("Down", 200, 100, "NATIVE", "xpath=//*[@text='Camera']", 0, 1000, 5, true);
        Assert.assertTrue(client.isElementFound("NATIVE", "xpath=//*[@text='Camera api1']"));
    }

    @After
    public void tearDown(){
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        client.deviceAction("home");
        client.generateReport(false);
        // Releases the client so that other clients can approach the agent in the near future.
        client.releaseClient();
    }


}
