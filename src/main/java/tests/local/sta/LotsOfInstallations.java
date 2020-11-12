package tests.local.sta;

import com.experitest.client.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class LotsOfInstallations {
    private String host = "localhost";
    private int port = 8889;
    private String projectBaseDirectory = "C:\\Users\\michael.baran\\workspace\\project4";
    protected Client client = null;
    protected int iterations = 1;
    protected String device = "adb:A0001";


    @Before
    public void setUp(){
        client = new Client(host, port, true);
        client.setProjectBaseDirectory(projectBaseDirectory);
        client.setReporter("xml", "reports", "generic");
    }

    @Test
    public void install(){
        client.setDevice(device);
        //        String apkToInstall = "cloud:com.experitest.ExperiBank";
        int count = 0;
        String apkToInstall = "C:\\Users\\michael.baran\\IdeaProjects\\autoProj5\\resources\\apk\\eribank.apk";
//        String apkToInstall = "resources\\apk\\eribank.apk";
        for (int i = 0; i < iterations; i++) {
            client.setProperty("android.install.grant.permissions", "true");
            client.setProperty("android.instrument.fingerprint.secure", "true");
            client.install(apkToInstall, true, false);
            count++;
            printPercentage(count);
            client.uninstall("com.experitest.ExperiBank");
            client.install(apkToInstall, false, false);
            count++;
            printPercentage(count);
            client.uninstall("com.experitest.ExperiBank");
            client.setProperty("android.instrument.fingerprint.secure", "false");
            client.setProperty("android.instrumentation.camera", "true");
            client.setProperty("android.instrumentation.camera2", "true");
            client.install(apkToInstall, true, false);
            count++;
            printPercentage(count);
            client.uninstall("com.experitest.ExperiBank");
            client.install(apkToInstall, false, false);
            count++;
            printPercentage(count);
            client.uninstall("com.experitest.ExperiBank");
            client.setProperty("android.instrument.fingerprint.secure", "true");
            client.setProperty("android.instrumentation.camera", "true");
            client.setProperty("android.instrumentation.camera2", "true");
            client.install(apkToInstall, true, false);
            count++;
            printPercentage(count);
            client.uninstall("com.experitest.ExperiBank");
            client.install(apkToInstall, false, false);
            count++;
            printPercentage(count);
            client.uninstall("com.experitest.ExperiBank");
        }
    }

    private void printPercentage(int count) {
        System.out.println(device + " >>> " + 100 * (double) count /(6 * iterations)  + "%");
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
