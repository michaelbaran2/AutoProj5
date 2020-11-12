package base;

import base.CloudAPI.Session;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import exceptions.NoDevicesFoundException;
import exceptions.TestNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class RunProperties {
    private String url;
    private String accessKey;
    private boolean onCloud;
    private String[] deviceSNs;
    private boolean seekPhones;
    private boolean seekTablets;
    private boolean seekTvs;
    private boolean seekChromebooks;
    int iterations;
    private boolean measureTiming; // For when timing measurements need to be taken
    private String[] testClasses;


    public RunProperties(String url, String accessKey, boolean onCloud, String[] deviceSNs, boolean seekPhones, boolean seekTablets, boolean seekTvs, boolean seekChromebooks, int iterations, boolean measureTiming, String[] testClasses) {
        this.url = url;
        this.accessKey = accessKey;
        this.onCloud = onCloud;
        this.deviceSNs = deviceSNs;
        this.seekPhones = seekPhones;
        this.seekTablets = seekTablets;
        this.seekTvs = seekTvs;
        this.seekChromebooks = seekChromebooks;
        if (iterations <= 0) {
            this.iterations = 1;
            AutoProject.LOGGER.info("Illegal iterations number was detected, will run for 1 iteration.");
        }
        else {
            this.iterations = iterations;
        }
        this.measureTiming = measureTiming;
        this.testClasses = testClasses;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getUrl() {
        return url;
    }

    public boolean getOnCloud() {
        return this.onCloud;
    }

    public String[] getDeviceSNs() {
        return this.deviceSNs;
    }

    public String[] getTestClasses() {
        return this.testClasses;
    }

    @PostConstruct
    public void setDeviceSNs() throws UnirestException, IOException, InterruptedException {
        if (this.deviceSNs.length == 0) {
            if (this.onCloud) {
                setCloudDevices();
            }
            else {
                setLocalDevices();
            }
        }
    }

    public boolean requiresMeasureTiming() {
        return this.measureTiming;
    }

    public int getIterations() {
        return this.iterations;
    }

    public void setCloudDevices() throws UnirestException {
        ArrayList<String> serialNumbers = new ArrayList<>();
        int deviceCount = 0;
        Session session = new Session(this.url, this.accessKey);
        HttpResponse<JsonNode> res = session.getAllDevices();
        JSONArray jsonArray = (JSONArray) res.getBody().getObject().get("data");
        for (Object o: jsonArray) {
            JSONObject deviceJson = (JSONObject) o;
            if (deviceJson.getString("deviceOs").equalsIgnoreCase("Android") && deviceJson.getString("displayStatus").equalsIgnoreCase("Available")) {
//            if (deviceJson.getString("displayStatus").equalsIgnoreCase("Available")) {
                if (this.seekPhones && deviceJson.getString("deviceCategory").equalsIgnoreCase("PHONE")) {
                    serialNumbers.add(deviceJson.getString("udid"));
                    deviceCount++;
                }
                else if (this.seekTablets && deviceJson.getString("deviceCategory").equalsIgnoreCase("TABLET")) {
                    serialNumbers.add(deviceJson.getString("udid"));
                    deviceCount++;
                }
                else if (this.seekTvs && deviceJson.getString("deviceCategory").equalsIgnoreCase("STB")) {
                    serialNumbers.add(deviceJson.getString("udid"));
                    deviceCount++;
                }
                else if (this.seekChromebooks && deviceJson.getString("deviceCategory").equalsIgnoreCase("CHROMEBOOK")) {
                    serialNumbers.add(deviceJson.getString("udid"));
                    deviceCount++;
                }
            }
        }
        AutoProject.LOGGER.info("detected " + deviceCount + " available devices on " + this.url);
        this.deviceSNs = serialNumbers.toArray(new String[0]);
    }

    public void setLocalDevices() throws IOException, InterruptedException {
        ArrayList<String> serialNumbers = new ArrayList<>();
        int deviceCount = 0;
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", "adb devices");
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null && line.length() != 0) {
            String[] processedLine = line.split("\\s+");
            if (processedLine.length == 2) {
                if (processedLine[1].equalsIgnoreCase("device")) { // TODO: implement query locally using adb -s <SN> getprop ro.build.characteristics
                    serialNumbers.add(processedLine[0]);
                    deviceCount++;
                }
            }
        }
        int exitCode = process.waitFor();
        AutoProject.LOGGER.info("adb detected " + deviceCount + " online devices on the local machine");
        AutoProject.LOGGER.info("adb process exit with error code: " + exitCode);
        this.deviceSNs = serialNumbers.toArray(new String[0]);
    }

    @Override
    public String toString() {
        return "url: " + this.url +
                "\naccessKey: " + this.accessKey +
                "\nonCloud: " + this.onCloud +
                "\nDevices: " + Arrays.toString(this.deviceSNs) +
                "\nTestClasses: " + Arrays.toString(this.testClasses);
    }

    private void validateDevicesSNs() throws NoDevicesFoundException {
        if (this.deviceSNs.length == 0) {
            throw new NoDevicesFoundException("Device serial numbers array is empty");
        }
    }

    public static RunProperties fetchRunProperties(TestMapper testMapper) throws TestNotFoundException, NoDevicesFoundException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:app-context.xml");
        RunProperties runProperties = context.getBean("runProperties", RunProperties.class);
        context.close();
        testMapper.validateTests(runProperties.getTestClasses());
        runProperties.validateDevicesSNs();
        return runProperties;
    }


}
