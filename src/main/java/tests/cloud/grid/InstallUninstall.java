package tests.cloud.grid;
import com.experitest.client.*;
import org.junit.*;
import tests.BaseTest;
import java.util.Date;

public class InstallUninstall extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;
    protected int iterations = 5;
    protected String pkgName = "com.experitest.uicatalog";
    protected String apkToInstall = "cloud:" + pkgName;

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
        for (int i = 0; i < iterations; i++) {
            client.install(apkToInstall, true, false);
            client.uninstall(pkgName);
            System.out.println(new Date() + " >>>> " + 100 * (double) i / iterations + "%");
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





}
