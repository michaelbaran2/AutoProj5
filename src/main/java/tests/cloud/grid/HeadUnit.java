package tests.cloud.grid;
import com.experitest.client.*;
import org.junit.*;
import tests.BaseTest;

public class HeadUnit extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("HeadUnit test", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "HeadUnit test");
    }

    @Test
    public void HeadUnit(){
        client.setProperty("android.noninstrumented.webview", "true");
//        System.out.println("sleepy time");
//        client.sleep(60000);
//        String str2 = client.hybridRunJavascript("", 0, "var result = document.title");
//        System.out.println("str2 = " + str2);
        String str0 = client.hybridRunJavascript("HeadUnit", 0, "var result = document.title");
        System.out.println("str0 = " + str0);
        String str2 = client.hybridRunJavascript("", 0, "var result = document.title");
        System.out.println("str2 = " + str2);
//        str0 = client.hybridRunJavascript("HeadUnit", 0, "var result = document.title");
//        System.out.println("str0 = " + str0);
//        String str1 = client.hybridRunJavascript("HeadUnit", 0, "document.title='TitleChangeddd'; var result = document.title;");
//        System.out.println("str1 = " + str1);

//        client.launch("chrome:wikipedia.com", false, true);
//        client.install("cloud:com.experitest.uicatalog", false, false);
//        client.launch("com.experitest.uicatalog/.MainActivity", false, true);
//        client.swipeWhileNotFound("down", 100, 1000, "native", "xpath=//*[@text='Css WebView']", 0, 1000, 5, true);
//        String str4 = client.hybridRunJavascript("", 0, "var result = document.title");
//        System.out.println("str4 = " + str4);
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
