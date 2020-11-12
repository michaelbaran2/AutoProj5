package tests.cloud.grid.timing;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.MeasuringTest;


public class LaunchApp extends MeasuringTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){

        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("Launch Time Test", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Launch Time Test");
    }

    @Test
    public void launchApp() {
        client.install("cloud:com.experitest.ExperiBank", true, false);
        startTimer();
        for (int i = 0; i < 10; i++) {
            client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        }
        stopTimer("launch application");
    }

    @After
    public void tearDown(){
        client.generateReport(false);
        client.releaseClient();

    }
}
