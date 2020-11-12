package tests.cloud.grid.issues;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class SA35292 extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("SA35292", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "SA35292");
    }

    @Test
    public void clickOutOnElementOutOfView(){
        client.install("cloud:com.experitest.uicatalog/.MainActivity", false, false);
        client.launch("com.experitest.uicatalog/.MainActivity", false, true);
        client.elementListSelect("xpath=//*[@id='listView']", "text=ScrollViews", 0, true);
        client.elementListSelect("xpath=//*[@id='scrollList']", "text=ScrollVertical", 0, true);
        client.elementSwipeWhileNotFound("NATIVE", "xpath=//*[@id='main_scroll']", "Down", 100, 2000,
                                         "NATIVE", "xpath=//*[@id='first_text_view']", 0,
                                         1000, 1000, true);
        String text = client.elementGetProperty("NATIVE","id=first_text_view",0,"text");
        Assert.assertEquals("Expected text to be 'ANDROID'", "ANDROID", text);
        client.deviceAction("home");
    }

    @After
    public void tearDown(){
        client.generateReport(false);
        client.releaseClient();
    }
}
