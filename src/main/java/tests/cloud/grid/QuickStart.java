package tests.cloud.grid;
import com.experitest.client.*;
import org.junit.*;
import tests.BaseTest;

public class QuickStart extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("SeeTest Quick Start Test", "@serialNumber='" + this.deviceSN + "'", 720, 60*60000*12);
        client.setReporter("xml", "", "SeeTest Quick Start Test");
        client.uninstall("cloud:com.experitest.ExperiBank");
    }

    @Test
    public void quickStartInstallFromCloud(){
        client.setProperty("android.install.grant.permissions", "true");
        client.install("cloud:com.experitest.ExperiBank", true, false);
        spamInstall(client);
        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
//        client.deviceAction("unlock");
        client.elementSendText("NATIVE", "hint=Username", 0, "company");
        client.elementSendText("NATIVE", "hint=Password", 0, "company");
        client.click("NATIVE", "text=Login", 0, 1);
        client.click("NATIVE", "text=Make Payment", 0, 1);
        client.elementSendText("NATIVE", "hint=Phone", 0, "1234567");
        client.elementSendText("NATIVE", "hint=Name", 0, "Jon Snow");
        client.elementSendText("NATIVE", "hint=Amount", 0, "50");
        client.click("NATIVE", "hint=Country", 0, 1);
        client.click("NATIVE", "text=Select", 0, 1);
        client.click("NATIVE", "text=Switzerland", 0, 1);
        client.click("NATIVE", "text=Send Payment", 0, 1);
        client.click("NATIVE", "text=Yes", 0, 1);
        client.click("NATIVE", "text=Logout", 0, 1);
    }

    @Test
    public void quickStartInstallFromPath(){
        client.setProperty("android.install.grant.permissions", "true");
//        client.install(ERIBANK_ABS_PATH, false, false);
        client.install("resources\\apk\\eribank.apk", true, false);
        spamInstall(client);
        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        client.elementSendText("NATIVE", "hint=Username", 0, "company");
        client.elementSendText("NATIVE", "hint=Password", 0, "company");
        client.click("NATIVE", "text=Login", 0, 1);
        client.click("NATIVE", "text=Make Payment", 0, 1);
        client.elementSendText("NATIVE", "hint=Phone", 0, "1234567");
        client.elementSendText("NATIVE", "hint=Name", 0, "Jon Snow");
        client.elementSendText("NATIVE", "hint=Amount", 0, "50");
        client.click("NATIVE", "hint=Country", 0, 1);
        client.click("NATIVE", "text=Select", 0, 1);
        client.click("NATIVE", "text=Switzerland", 0, 1);
        client.click("NATIVE", "text=Send Payment", 0, 1);
        client.click("NATIVE", "text=Yes", 0, 1);
        client.click("NATIVE", "text=Logout", 0, 1);
    }


    @After
    public void tearDown(){
        client.deviceAction("home");
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        client.generateReport(false);
        // Releases the client so that other clients can approach the agent in the near future.
        client.releaseClient();
    }

}
