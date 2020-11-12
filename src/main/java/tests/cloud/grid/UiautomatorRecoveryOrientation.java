package tests.cloud.grid;
import com.experitest.client.*;
import org.junit.*;
import tests.BaseTest;


public class UiautomatorRecoveryOrientation extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;
    protected int iterations =5;
    protected String pkgName = "com.experitest.uiautomator.test";

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("UiautomatorRecoveryOrientation", "@serialNumber='" + this.deviceSN + "'", 720, 60*60000*12);
        client.setReporter("xml", "", "UiautomatorRecoveryOrientation");
    }

    @Test
    public void UiautomatorRecoveryOrientation(){
        for (int i = 0; i < iterations; i++) {
            killUiautomator();
            client.sendText("{LANDSCAPE}");
            client.sendText("{LANDSCAPE}");
            client.sendText("{PORTRAIT}");
        }
    }

    private void killUiautomator() {
        client.uninstall(pkgName);
        client.run("adb shell am force-stop " + pkgName);
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
