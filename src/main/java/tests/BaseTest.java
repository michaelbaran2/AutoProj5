package tests;

import base.AutoProject;
import com.experitest.client.Client;

public class BaseTest {
    protected static final String UICATALOG_PATH = "resources\\apk\\UICatalog.apk";
    protected static final String  UICATALOG_ABS_PATH = "C:\\Users\\michael.baran\\IdeaProjects\\autoProj5\\resources\\apk\\UICatalog.apk";
    protected static final String BOOP_PATH = "resources\\apk\\boop.apk";
    protected static final String ERIBANK_PATH = "resources\\apk\\eribank.apk";
    protected static final String APK_FOLDER_PATH = "resources\\apk\\";
    protected static final String UICATALOG_MAIN_ACTIVITY = "com.experitest.uicatalog/.MainActivity";


    protected String deviceSN = AutoProject.threadDeviceMapping.get(Thread.currentThread().getName());
    protected String accessKey = AutoProject.runProperties.getAccessKey();
    protected String url = AutoProject.runProperties.getUrl();


    protected void spamAllow(Client client) {
        client.sleep(2000);
        while(client.isElementFound("NATIVE", "xpath=//*[@text='Allow']")) {
            client.click("NATIVE", "xpath=//*[@text='Allow']", 0, 1);
            client.sleep(1000);
        }
        while(client.isElementFound("NATIVE", "xpath=//*[@text='ALLOW']")) {
            client.click("NATIVE", "xpath=//*[@text='ALLOW']", 0, 1);
            client.sleep(1000);
        }
        while(client.isElementFound("NATIVE", "xpath=//*[@text='While using the app']")) {
            client.click("NATIVE", "xpath=//*[@text='While using the app']", 0, 1);
            client.sleep(1000);
            client.click("NATIVE", "xpath=//*[@text='Allow']", 0, 1);
            client.sleep(1000);
        }

    }

    protected void spamInstall(Client client) {
        while(client.isElementFound("NATIVE", "//*[@text='Install']")) {
            client.click("NATIVE", "//*[@text='Install']", 0, 1);
        }
    }


}
