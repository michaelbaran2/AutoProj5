package tests.cloud.grid;
import com.experitest.client.*;
import org.junit.*;
import tests.BaseTest;

public class HybridRunJavaScript extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("HybridRunJavaScript test", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "HybridRunJavaScript test");
    }

    @Test
    public void HybridRunJavaScriptTest(){
//        client.hybridRunJavascript("HeadUnit");
//        String str0 = client.hybridRunJavascript("HeadUnit", 0, "var result = document.cookie;");
//        String str0 = client.hybridRunJavascript("HeadUnit", 0, "document.title = 'waba'; var result = document.title");

//        String str0 = client.hybridRunJavascript("", 0, "var result = document.title");
        client.launch("chrome:wikipedia.com", false, true);
        String str1 = client.hybridRunJavascript("", 0, "var result = document.title");

//        String str0 = client.hybridRunJavascript("HeadUnit", 0, "var result = document.getElementByClassName(\"search button_iconPart\").innerHTML;");
//        System.out.println("str0 = " + str0);
        System.out.println("str1 = " + str1);

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
