package tests.cloud.grid;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class SetClipboardTextTest extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("Set Clipboard Text Test", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Set Clipboard Text Test");
    }

    @Test
    public void setClipboardTextTest(){
        final String setText = "setClipboardText";
        client.install("cloud:com.experitest.uicatalog", true, false);
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.click("NATIVE","xpath=//*[@text='Text Fields']", 0,1);
        client.setClipboardText(setText);
        client.longClick("NATIVE", "hint=Normal Text field", 0,1, 1, 1);
        client.click("NATIVE", "xpath=//*[@text='Paste']", 0, 1);
        Assert.assertEquals("Text didn't much clipboard", client.elementGetText("NATIVE", "hint=Normal Text field", 0), setText);
    }

    @After
    public void tearDown(){
        client.generateReport(false);
        client.releaseClient();
    }
}
