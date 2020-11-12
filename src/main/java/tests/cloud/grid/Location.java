package tests.cloud.grid;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.*;
import tests.BaseTest;

public class Location extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(this.accessKey, this.url);
    }

    public void initTest(String testName, String query) {
        client = grid.lockDeviceForExecution(testName, query, 720, 60*60000*12);
        client.setReporter("xml", "", testName);

    }

    @Test
    public void setGetLocationGpsNetwork(){
        initTest("Location Test: GPS,Network = true", "@serialNumber='" + this.deviceSN + "'");
        client.setProperty("location.service.gps", "true");
        client.setProperty("location.service.network", "true");
        testLocationValues();
    }

//    @Ignore
//    @Test
//    public void setGetLocationNoGpsNoNetwork(){
//        initTest("Location Test: GPS,Network = false", "@serialNumber='" + this.deviceSN + "'");
//        client.setProperty("location.service.gps", "false");
//        client.setProperty("location.service.network", "false");
//        testLocationValues();
//    }

    @Test
    public void setGetLocationGps(){
        initTest("Location Test: GPS = true, Network = false", "@serialNumber='" + this.deviceSN + "'");
        client.setProperty("location.service.gps", "true");
        client.setProperty("location.service.network", "false");
        testLocationValues();
    }

    @Test
    public void setGetLocationNetwork(){
        initTest("Location Test: GPS = false, Network = true", "@serialNumber='" + this.deviceSN + "'");
        client.setProperty("location.service.gps", "false");
        client.setProperty("location.service.network", "true");
        testLocationValues();
    }



    public void testLocationValues() {
        String lat = "36.296238";
        String lon = "-91.933594";
        client.setLocation(lat, lon); // USA
        String[] loc = client.getLocation().split(",");
        Assert.assertEquals("Coordinate doesn't match" , Double.parseDouble(lat), Double.parseDouble(loc[0]), 1.0E-6);
        Assert.assertEquals("Coordinate doesn't match" ,Double.parseDouble(lon), Double.parseDouble(loc[1]), 1.0E-6);

        lat = "32.22222";
        lon = "34.3";
        client.setLocation(lat, lon);
        loc = client.getLocation().split(",");
        Assert.assertEquals("Coordinate doesn't match" , Double.parseDouble(lat), Double.parseDouble(loc[0]), 1.0E-6);
        Assert.assertEquals("Coordinate doesn't match" ,Double.parseDouble(lon), Double.parseDouble(loc[1]), 1.0E-6);

        lat = "0";
        lon = "0";
        client.setLocation(lat, lon);
        loc = client.getLocation().split(",");
        Assert.assertEquals("Coordinate doesn't match" , Double.parseDouble(lat), Double.parseDouble(loc[0]), 1.0E-6);
        Assert.assertEquals("Coordinate doesn't match" ,Double.parseDouble(lon), Double.parseDouble(loc[1]), 1.0E-6);
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
