package tests.local.sta;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class InstallLikeADong {
protected Client client = null;
    //    protected GridClient grid = null;
    protected String host = "localhost";
    protected int port = 8889;
    protected String deviceName = "adb:samsung SM-G955F(1)";
    protected static final String  UICATALOG_ABS_PATH = "C:\\Users\\michael.baran\\IdeaProjects\\autoProj5\\resources\\apk\\UICatalog.apk";

    @Before
    public void setUp(){
//        grid = new GridClient(this.accessKey, this.url);
        client = new Client(host, port, true);
        client.setDevice(deviceName);
        client.uninstall("com.accuweather.android");
    }

//
    @Test
    public void installFromPath(){
        client.setProperty("android.install.grant.permissions", "true");
        client.install("C:\\Users\\MichaelBaran\\IdeaProjects\\AutoProj5\\resources\\apk\\com.accuweather.android_.activities.SplashActivity_ver_70501074.apk", false, false);
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
