package tests.cloud.grid.regression;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LongRun extends BaseTest {
    protected long TOTAL_TIME = 36000000; // = 10 hours
    protected Client client = null;
    protected GridClient grid = null;
    //    protected String query = "@emulator='true'";
    protected String query = "@serialNumber='" + this.deviceSN + "'";

    @Before
    public void setUp(){
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("LOOOONG RUN", query, 720, 60*60000*12);
        client.setReporter("xml", "", "LOOOONG RUN");
    }

    @Test
    public void LongRun() throws FileNotFoundException {
        client.setProperty("location.service.gps", "true");
        long start = System.currentTimeMillis();
//        while (System.currentTimeMillis() - start < TOTAL_TIME) {
        client.startLoggingDevice("longrunoutput" + File.separator + this.deviceSN + "-logcat.log");
        client.deviceAction("Unlock");
        String lat = "32.22222";
        String lon = "34.3";
        client.setLocation(lat, lon);
        String[] loc = client.getLocation().split(",");
        Assert.assertEquals("Coordinate doesn't match" , Double.parseDouble(lat), Double.parseDouble(loc[0]), 1.0E-6);
        Assert.assertEquals("Coordinate doesn't match" ,Double.parseDouble(lon), Double.parseDouble(loc[1]), 1.0E-6);
        client.install("cloud:com.experitest.ExperiBank", true, false);
        try {
            client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        }
        catch (Exception e){
            File dirToTestReport  = new File("longrunoutput" + File.separator + "InstalledApplications" + this.deviceSN + ".csv");
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
        client.deviceAction("Recent Apps");
        client.deviceAction("Back");
        client.reboot(300000);
        client.stopLoggingDevice();
        client.deviceAction("home");
//        }
    }


    @After
    public void tearDown(){
        client.generateReport(false);
        client.releaseClient();
    }
}
