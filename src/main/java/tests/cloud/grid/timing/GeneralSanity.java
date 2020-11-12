package tests.cloud.grid.timing;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;
import tests.MeasuringTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GeneralSanity extends MeasuringTest {
    protected Client client = null;
    protected GridClient grid = null;
    protected String query = "@emulator='true'";
    protected int iterations = 1;
//    protected String query = "@serialNumber='" + this.deviceSN + "'";

    @Before
    public void setUp(){
        startTimer();
        grid = new GridClient(this.accessKey, this.url);
    }

    @Test
    public void generalSanityTest() throws FileNotFoundException {
        client = grid.lockDeviceForExecution("generalSanityTest", query, 10, 50000);
        client.setReporter("xml", "", "generalSanityTest");
        for (int i = 0; i < iterations; i++) {
            client.startLoggingDevice("logs" + File.separator + this.deviceSN + "-logcat.log");
            client.deviceAction("Unlock");
            client.setLocation("36.296238", "-91.933594"); // USA
            client.install("cloud:com.experitest.ExperiBank", true, false);
            try {
                client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
            }
            catch (Exception e){
                File dirToTestReport  = new File("logs" + File.separator + "InstalledApplications" + this.deviceSN + ".csv");
                PrintWriter testFile = new PrintWriter(dirToTestReport);
                testFile.println(client.getSessionID());
                testFile.flush();
                testFile.println(client.getInstalledApplications());
                testFile.flush();
                e.printStackTrace();
                throw e;
            }
            client.closeKeyboard();
            client.applicationClose("com.experitest.ExperiBank/.LoginActivity");
            client.uninstall("cloud:com.experitest.ExperiBank");
            client.getDeviceLog();
            client.applicationClearData("com.experitest.ExperiBank/.LoginActivity");
            client.deviceAction("Home");
//        client.reboot(240000);
            client.stopLoggingDevice();
        }
    }

    @After
    public void tearDown(){
        client.generateReport(false);
        client.releaseClient();
        stopTimer("generalSanityTest");
    }
}
