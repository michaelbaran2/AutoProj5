package tests.cloud.grid;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class InstallTest extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;
    private String[] builds = {"5.9.9-free", "6.0.0-free", "6.0.1-free", "6.0.2-free", "6.0.3-free", "6.0.4-free", "6.0.5-free", "6.0.6-free",
                               "6.0.7-free", "6.0.8-free", "6.0.9-free", "6.1.0-free", "6.1.1-free", "6.1.2-free", "6.1.3-free", "6.1.4-free",
                                "6.0.10-free", "5.8.6-free", "6.1.5-free", "6.1.6-free", "6.1.7-free", "6.1.8-free", "6.1.9-free", "6.1.10-free",};

    @Before
    public void setUp() {
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("SeeTest Install Test", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "SeeTest Install Test");
    }

    @Test
    public void installInstrumented() {
        String activity = "cloud:com.accuweather.android/com.accuweather.onboarding.SplashScreen";
        for (String build: builds) {
            String toInstall = activity + ":" + build;
            client.install(toInstall, true, false);
            client.launch(activity, true, true);
            client.deviceAction("home");
            //client.uninstall("cloud:com.accuweather.android");
        }

    }

    @Test
    public void installNonInstrumented() {
        String activity = "cloud:com.accuweather.android/com.accuweather.onboarding.SplashScreen";
        for (String build: builds) {
            String toInstall = activity + ":" + build;
            client.install(toInstall, false, false);
            client.launch(activity, false, true);
            client.deviceAction("home");
            //client.uninstall("cloud:com.accuweather.android");
        }
    }

    @After
    public void tearDown() {
        client.generateReport(false);
        client.releaseClient();
    }
}
