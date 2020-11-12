package tests.cloud.grid;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class PickersTest extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
    }

    @Test
    public void datePickerTest(){
        client = grid.lockDeviceForExecution("Grid Date Picker Test", "@serialNumber='" + this.deviceSN + "'", 720, 60*60000*12);
        client.setReporter("xml", "", "Grid Date Picker Test");
        client.install("cloud:com.experitest.uicatalog", true, false);
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("down", 100, 1000, "native", "//*[@text='Pickers']", 0, 1000, 5, true);
        client.sleep(5000);

    }

    @Test
    public void timePickerTest(){
        client = grid.lockDeviceForExecution("Grid Time Picker Test", "@serialNumber='" + this.deviceSN + "'", 720, 60*60000*12);
        client.setReporter("xml", "", "Grid Time Picker Test");
        client.install("cloud:com.experitest.uicatalog", true, false);
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("down", 100, 1000, "native", "//*[@text='Pickers']", 0, 1000, 5, true);
        client.sleep(5000); // //*[@id='timePicker']
        client.elementSetProperty("NATIVE", "xpath=//*[@id='timePicker']", 0, "time", "03.32");
        String time = client.elementGetProperty("NATIVE", "xpath=//*[@id='timePicker']", 0, "time");
        Assert.assertEquals("Got wrong time on picker", "3.32", time);
    }

    @After
    public void tearDown(){
        client.deviceAction("home");
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        client.generateReport(false);
        // Releases the client so that other clients can approach the agent in the near future.
        client.releaseClient();
    }
}
