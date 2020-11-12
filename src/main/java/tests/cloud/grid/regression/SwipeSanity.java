package tests.cloud.grid.regression;

import base.AutoProject;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SwipeSanity extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;
    //    protected String query = "@emulator='true'";
    protected String query = "@serialNumber='" + this.deviceSN + "'";

    @Before
    public void setUp(){
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("basicSwipeSanityTest", query, 720, 60*60000*12);
        client.setReporter("xml", "", "basicSwipeSanityTest");
    }


    @Test
    public void basicSwipeSanityTest() {
        client.install("cloud:com.experitest.uicatalog", false, false);
        client.launch("com.experitest.uicatalog/.MainActivity", false, true);
        client.sleep(3000);
        client.click("NATIVE", "xpath=//*[@text='Lists']", 0, 1);
        client.sleep(3000);
        client.swipeWhileNotFound("down", 200, 10000, "Native", "xpath=//*[@text='Raz']", 0, 0, 5, true);
        client.sleep(3000);
        Assert.assertTrue("Didn't click the right element", client.isElementFound("NATIVE", "//*[@text='12 is selected']"));
        client.click("NATIVE", "//*[@text='OK']", 0, 1);
        client.launch("com.experitest.uicatalog/.MainActivity", false, true);
        client.sleep(3000);
        client.click("NATIVE", "xpath=//*[@text='Lists']", 0, 1); //
        client.swipeWhileNotFound("down", 200, 10000, "Native", "xpath=//*[@text='Dangolan']", 0, 0, 5, true);
        client.sleep(3000);
        Assert.assertTrue("Didn't click the right element", client.isElementFound("NATIVE", "//*[@text='26 is selected']"));
        client.click("NATIVE", "//*[@text='OK']", 0, 1);
    }

    @After
    public void tearDown(){
        client.deviceAction("home");
        client.generateReport(false);
        client.releaseClient();
    }
}
