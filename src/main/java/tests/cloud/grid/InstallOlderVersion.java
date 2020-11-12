package tests.cloud.grid;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class InstallOlderVersion extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp() {
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("SeeTest Install Older Version Test", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "SeeTest Install Older Version Test");
        client.uninstall("cloud:com.example.myapplication");
    }

    @Test
    public void installInstrumented() {
            client.install("cloud:com.example.myapplication/.MainActivity:1.1", true,false);
            client.install("cloud:com.example.myapplication/.MainActivity:1.0",false, false);
    }

    @Test
    public void installNonInstrumented() {
        client.install("cloud:com.example.myapplication/.MainActivity:1.1", false, false);
        client.install("cloud:com.example.myapplication/.MainActivity:1.0", false, false);
    }

    @After
    public void tearDown() {
        client.generateReport(false);
        client.releaseClient();
    }
}
