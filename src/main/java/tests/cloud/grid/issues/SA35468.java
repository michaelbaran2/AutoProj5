package tests.cloud.grid.issues;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class SA35468 extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp() {
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("SA35468", "@emulator='true'", 10, 50000);
        client.setReporter("xml", "", "SA35468");
    }

    @Test
    public void clickOnElementUnderNabBar() {
        client.install("cloud:com.experitest.uicatalog/.MainActivity", false, false);
        client.launch("com.experitest.uicatalog/.MainActivity", false, true);
        client.sleep(3000);
        client.click("NATIVE", "xpath=//*[@text='Lists']", 0, 1);
        client.sleep(3000);
        client.swipeWhileNotFound("down", 200, 10000, "Native", "xpath=//*[@text='Raz']", 0, 0, 5, true);
        client.sleep(3000);
        Assert.assertTrue("Didn't click the right element", client.isElementFound("NATIVE", "//*[@text='15 is selected']"));
        client.click("NATIVE", "//*[@text='OK']", 0, 1);
        client.install("cloud:com.experitest.uicatalog/.MainActivity", false, false);
        client.launch("com.experitest.uicatalog/.MainActivity", false, true);
        client.sleep(3000);
        client.click("NATIVE", "xpath=//*[@text='Lists']", 0, 1); //
        client.swipeWhileNotFound("down", 200, 10000, "Native", "xpath=//*[@text='Dangolan']", 0, 0, 5, true);
        client.sleep(3000);
        Assert.assertTrue("Didn't click the right element", client.isElementFound("NATIVE", "//*[@text='26 is selected']"));
        client.click("NATIVE", "//*[@text='OK']", 0, 1);
    }

//    @Test
//    public void swipeToElementUnderNabBar() {
//        client.install("cloud:com.experitest.uicatalog/.MainActivity", false, false);
//        client.launch("com.experitest.uicatalog/.MainActivity", false, true);
//        client.sleep(3000);
//        client.click("NATIVE", "xpath=//*[@text='Lists']", 0, 1);
//        client.sleep(3000);
//        client.swipeWhileNotFound("down", 200, 10000, "Native", "xpath=//*[@text='Raz']", 0, 0, 5, true);
//        client.sleep(3000);
//        Assert.assertTrue("Didn't click the right element", client.isElementFound("NATIVE", "//*[@text='15 is selected']"));
//    }
//
//    @Test
//    public void clickOutOnElementOutOfView(){
//        client.install("cloud:com.experitest.uicatalog/.MainActivity", false, false);
//        client.launch("com.experitest.uicatalog/.MainActivity", false, true);
//        client.elementListSelect("xpath=//*[@id='listView']", "text=ScrollViews", 0, true);
//        client.elementListSelect("xpath=//*[@id='scrollList']", "text=ScrollVertical", 0, true);
//        client.elementSwipeWhileNotFound("NATIVE", "xpath=//*[@id='main_scroll']", "Down", 100, 2000,
//                "NATIVE", "xpath=//*[@id='first_text_view']", 0,
//                1000, 1000, true);
//        String text = client.elementGetProperty("NATIVE","id=first_text_view",0,"text");
//        Assert.assertEquals("Expected text to be 'ANDROID'", "ANDROID", text);
//        client.deviceAction("home");
//    }

    @After
    public void tearDown() {
        client.deviceAction("home");
        client.generateReport(false);
        client.releaseClient();
    }
}
