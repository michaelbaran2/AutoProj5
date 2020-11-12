package tests.cloud.grid;
import com.experitest.client.*;
import org.junit.*;
import tests.BaseTest;

public class MockFingerprintBiometricPrompt extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;


    @Before
    public void setUp(){
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("Mock fingerprint biometric prompt test", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Mock fingerprint biometric prompt test");

    }

    @Test
    public void mockFPTest(){
        client.setProperty("android.install.grant.permissions", "true");
        client.setProperty("android.instrument.fingerprint.secure", "true");
        if(client.install(APK_FOLDER_PATH + "bioAuthSampleApp.apk", true, false)){
            // If statement
        }
        client.launch("com.example.android.fingerprintdialog/com.example.android.bioappauth.MainActivity", true, false);
        client.setAuthenticationReply("AUTHENTICATION_SUCCEEDED", 0);
        client.click("NATIVE", "xpath=//*[@text='Purchase']", 0, 1);
        Assert.assertTrue("Could find 'Purchase successful' message", client.isElementFound("NATIVE", "//*[@text='Purchase successful']"));
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
