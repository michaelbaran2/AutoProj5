package tests.cloud.grid;
import com.experitest.client.*;
import org.junit.*;
import tests.BaseTest;

public class SavePreviewFrames extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;
    private String QR_SCANNER_APP = "resources\\apk\\zxing-sample-debug.apk";

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("Save preview frames test", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Save preview frames test");
    }

//    @Test
//    public void previewCallbackFalse(){
//        client.setProperty("android.install.grant.permissions", "true");
//        client.setProperty("android.instrumentation.camera", "true");
//        client.install(QR_SCANNER_APP, true, false);
//        client.launch("me.dm7.barcodescanner.zxing.sample/.MainActivity", true, true);
//        client.simulateCapture("resources\\properties3.txt");
//        client.click("NATIVE", "//*[@text='Simple Activity Sample']", 0, 1);
//        try {
//            Thread.sleep(120000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(client.getDeviceLog());
//    }

    @Test
    public void previewCallbackTrue(){
        client.setProperty("android.install.grant.permissions", "true");
        client.setProperty("android.instrumentation.camera", "true");
        client.setProperty("android.instrumentation.camera2", "true");
//        client.install("resources\\apk\\UICatalog.apk", true, false);
        client.install("cloud:com.experitest.uicatalog", true, false);
        client.launch("com.experitest.uicatalog/.MainActivity", true, true);
        client.swipeWhileNotFound("Down", 200, 100, "NATIVE", "xpath=//*[@text='Camera']", 0, 1000, 5, true);
        spamAllow(client);
        client.simulateCapture("resources\\properties.txt");
        client.click("NATIVE", "//*[@text='Camera api2 (JPEG)']", 0, 1);
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(client.getDeviceLog());

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
