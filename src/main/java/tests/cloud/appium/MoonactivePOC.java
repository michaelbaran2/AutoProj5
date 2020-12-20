package tests.cloud.appium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import tests.BaseTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class MoonactivePOC extends BaseTest {
    protected long TOTAL_TIME = 60*1000*60*7;
    protected AndroidDriver<AndroidElement> driver = null;
    DesiredCapabilities dc = new DesiredCapabilities();
    HashMap<String, Integer> serialToPort = new HashMap<String, Integer>() {
        {
            put("99161FFAZ007RY", 13001); put("ce12160cf49a1a2104", 13002);
//            put("668bbfe5", 13001); put("031603c437ba1901", 13002);
        }
    };


    @Before
    public void setUp() throws MalformedURLException {
        dc.setCapability("testName", "POC");
        dc.setCapability("accessKey", this.accessKey);
        dc.setCapability("deviceQuery", "@serialNumber='" + this.deviceSN + "'");
        dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.echo_app/.MainActivity");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.echo_app");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
        driver = new AndroidDriver<>(new URL(this.url + "/wd/hub"), dc);

    }

    @Test
    public void testA() {
        long start = System.currentTimeMillis();
        do {
            String response = "";
            final String expectedResponse = String.valueOf(System.currentTimeMillis());
            try (Socket socket = new Socket("localhost", serialToPort.get(this.deviceSN))) {
                socket.getOutputStream().write((expectedResponse + "\n").getBytes(StandardCharsets.UTF_8));
                final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                response = reader.readLine();
                int retries = 0;
                while (response == null && retries < 2) {
                    socket.getOutputStream().write((expectedResponse + "\n").getBytes(StandardCharsets.UTF_8));
                    response = reader.readLine();
                    retries++;
                    Thread.sleep(100);
                }
                assertEquals(expectedResponse, response);
                System.out.println(new Date() + ": " + this.deviceSN + ": " + expectedResponse + ", " + response);
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
                driver.quit();
                fail(e.getMessage());
            }
        } while (System.currentTimeMillis() - start < TOTAL_TIME);
    }



    @After
    public void tearDown() {
//        System.out.println(MoonactivePOC.class.getName() + ": Report URL for device 09241FDD40079M: "+ driver.getCapabilities().getCapability("reportUrl"));
        System.out.println(MoonactivePOC.class.getName() + ": Report URL for device " + this.deviceSN + ": "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }
}
