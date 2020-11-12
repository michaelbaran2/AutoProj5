package tests.cloud.grid;
import com.experitest.client.*;
import org.junit.*;
import tests.BaseTest;

public class InstallShit extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;
    protected int iterations = 5;

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("Install Shit", "@serialNumber='" + this.deviceSN + "'", 720, 60*60000*12);
        client.setReporter("xml", "", "Install Shit");
    }

    @Test
    public void installShit(){
        String apkToInstall = "cloud:com.experitest.ExperiBank";
        int count = 0;
//        String apkToInstall = "cloud:eu.tsystems.mms.tic.mdc.app.android/.HomeActivity";
        String pkgName = "com.experitest.uicatalog";
        boolean[] options = new boolean[] {false, true};
        for (int i = 0; i < iterations; i++) {
            client.uninstall(pkgName);
            for (boolean flagFingerprint: options) {
                for (boolean flagCamera1: options) {
                    for (boolean flagCamera2: options) {
                        manageInstall(apkToInstall, pkgName, flagFingerprint, flagCamera1, flagCamera2);
                        count++;
                        printPercentage(count);
                    }
                }
            }
        }
    }

    @After
    public void tearDown(){
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        client.generateReport(false);
        // Releases the client so that other clients can approach the agent in the near future.
        client.releaseClient();
    }

    private void printPercentage(int count) {
        System.out.println(deviceSN + " >>> " + 100 * (double) count /(8 * iterations)  + "%");
    }

    private void manageInstall(String appToInstall, String pkgName, boolean fingerprint, boolean camera1, boolean camera2) {
        String fingerprintProperty = "android.instrument.fingerprint.secure";
        String cameraProperty = "android.instrumentation.camera";
        String camera2Property = "android.instrumentation.camera2";
        client.setProperty(fingerprintProperty, Boolean.toString(fingerprint));
        client.setProperty(cameraProperty, Boolean.toString(camera1));
        if (camera1) {
            client.setProperty(camera2Property, Boolean.toString(camera2));
        }
        else {
            client.setProperty(camera2Property, "false");
        }
        client.install(appToInstall, true, false);
        client.uninstall(pkgName);
    }



}
