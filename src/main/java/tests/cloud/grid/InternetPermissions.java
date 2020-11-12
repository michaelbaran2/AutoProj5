package tests.cloud.grid;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class InternetPermissions extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;
    private String EXPECTED_EXCEPTION_MESSAGE = "Exception caught while executing install: com.experitest.image.agent.InternalException: The APK file should have an 'Internet Permission' in order to launch the application in instrumented mode.";
    private String APK_WITHOUT_PERMISSIONS = "resources\\apk\\bla.apk";
    private String APK_WITH_PERMISSIONS = "resources\\apk\\bla_internet_permissions.apk";

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
    }

    @Test
    public void installInstrumentedWithoutPermissions(){
        String apkToInstall = APK_WITHOUT_PERMISSIONS;
        client = grid.lockDeviceForExecution("Install instrumented apk without internet permissions", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Install instrumented apk without internet permissions");
        client.setProperty("android.install.grant.permissions", "true");
//        Assert.assertFalse("Instrumented install of " + apkToInstall + " shouldn't succeed", client.install(apkToInstall, true, false));
        try {
            client.install(apkToInstall, true, false);
        }
        catch (Exception e) {
            Assert.assertEquals("Install should fail with correct exception", EXPECTED_EXCEPTION_MESSAGE, e.getMessage());
        }
    }


    @Test
    public void installNonInstrumentedWithoutPermissions(){
        String apkToInstall = APK_WITHOUT_PERMISSIONS;
        client = grid.lockDeviceForExecution("Install non-instrumented apk without internet permissions", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Install non-instrumented apk without internet permissions");
        client.setProperty("android.install.grant.permissions", "true");
        Assert.assertTrue("Non-instrumented install of " + apkToInstall + " should succeed", client.install(apkToInstall, false, false));
    }

    @Test
    public void installInstrumentedWithPermissions(){
        String apkToInstall = APK_WITH_PERMISSIONS;
        client = grid.lockDeviceForExecution("Install instrumented apk with internet permissions", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Install instrumented apk with internet permissions");
        client.setProperty("android.install.grant.permissions", "true");
        Assert.assertTrue("Instrumented install of " + apkToInstall + " should succeed", client.install(apkToInstall, true, false));
    }


    @Test
    public void installNonInstrumentedWithPermissions(){
        String apkToInstall = APK_WITH_PERMISSIONS;
        client = grid.lockDeviceForExecution("Install non-instrumented apk with internet permissions", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Install non-instrumented apk with internet permissions");
        client.setProperty("android.install.grant.permissions", "true");
        Assert.assertTrue("Non-instrumented install of " + apkToInstall + " should succeed", client.install(apkToInstall, false, false));
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

