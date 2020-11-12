package tests.cloud.grid.timing.simulatecapture;
import base.AutoProject;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;
import tests.MeasuringTest;


public class CameraAPITest extends MeasuringTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("Camera API test", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Camera API test");
    }

    @Test
    public void camera1Test(){
        client.setProperty("android.install.grant.permissions", "true");
        client.setProperty("android.instrumentation.camera", "true");
//        client.install("resources\\apk\\UICatalog.apk", true, false);
        client.install("cloud:com.experitest.uicatalog", true, false);
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("Down", 200, 100, "NATIVE", "xpath=//*[@text='Camera']", 0, 1000, 5, true);
        spamAllow(client); // This call is redundant when installing from path.
        client.click("NATIVE", "xpath=//*[@text='Camera api1']", 0, 1);
        client.sleep(10000);
        startTimer();
        client.simulateCapture("resources\\HelloTest.jpg");
        stopTimer("Simulate capture");
        client.sleep(5000);
        boolean isFoundText = client.isElementFound("TEXT", "HELLO", 0);
        Assert.assertTrue("Expected to find 'hello' on screen.", isFoundText);
    }

    @After
    public void tearDown(){
        client.deviceAction("home");
        client.generateReport(false);
        client.releaseClient();
    }

}
