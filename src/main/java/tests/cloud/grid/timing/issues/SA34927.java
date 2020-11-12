package tests.cloud.grid.timing.issues;

import base.AutoProject;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
import com.experitest.client.MobileListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import tests.MeasuringTest;


public class SA34927 extends MeasuringTest {
    protected Client client = null;
    protected GridClient grid = null;
    protected static final String FOUND_MESSAGE =  "MobileListener found ";

    @Before
    public void setUp(){
        grid = new GridClient(this.accessKey, this.url);
    }


    @Test
    public void singleMobileListenerTest() {
        client = grid.lockDeviceForExecution("Single mobile listener test", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Single mobile listener test");
        client.install(UICATALOG_PATH, true, false);
        client.launch(UICATALOG_MAIN_ACTIVITY, true, true);
        client.addMobileListener("WEB", "xpath=//*[@id='sb_form_q']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.swipeWhileNotFound("down", 100, 1000, "native", "xpath=//*[@text='WebElements']", 0, 1000, 5, false);
        for (int i = 0; i < 10; i++) {
            startTimer();
            client.click("NATIVE", "xpath=//*[@text='WebElements']", 0, 1);
            stopTimer("Click");
            client.sleep(3000);
            client.deviceAction("back");
        }
    }

    @Ignore
    @Test
    public void twoMobileListenersTest() {
        client = grid.lockDeviceForExecution("Two mobile listener test", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Two mobile listener test");
        client.install(UICATALOG_PATH, true, false);
        client.launch(UICATALOG_MAIN_ACTIVITY, true, true);
        client.addMobileListener("WEB", "xpath=//*[@id='sb_form_q']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.addMobileListener("WEB", "xpath=//*[@id='searchInput']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.swipeWhileNotFound("down", 100, 1000, "native", "xpath=//*[@text='WebElements']", 0, 1000, 5, false);
        for (int i = 0; i < 10; i++) {
            startTimer();
            client.click("NATIVE", "xpath=//*[@text='WebElements']", 0, 1);
            stopTimer("Click");
            client.sleep(3000);
            client.deviceAction("back");
        }
    }

    @Ignore
    @Test
    public void threeMobileListenersTest() {
        client = grid.lockDeviceForExecution("Three mobile listener test", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Three mobile listener test");
        client.install(UICATALOG_PATH, true, false);
        client.launch(UICATALOG_MAIN_ACTIVITY, true, true);
        client.addMobileListener("WEB", "xpath=//*[@id='sb_form_q']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.addMobileListener("WEB", "xpath=//*[@id='searchInput']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.addMobileListener("WEB", "xpath=//*[@class='android.widget.Image']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.swipeWhileNotFound("down", 100, 1000, "native", "xpath=//*[@text='WebElements']", 0, 1000, 5, false);
        for (int i = 0; i < 10; i++) {
            startTimer();
            client.click("NATIVE", "xpath=//*[@text='WebElements']", 0, 1);
            stopTimer("Click");
            client.sleep(3000);
            client.deviceAction("back");
        }
    }

    @Ignore
    @Test
    public void tenMobileListenersTest() {
        client = grid.lockDeviceForExecution("Ten mobile listeners test", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Ten mobile listeners test");
        client.install(UICATALOG_PATH, true, false);
        client.launch(UICATALOG_MAIN_ACTIVITY, true, true);
        client.addMobileListener("WEB", "xpath=//*[@id='sb_form_q']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.addMobileListener("WEB", "xpath=//*[@id='searchInput']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.addMobileListener("WEB", "xpath=//*[@class='android.widget.Image']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.addMobileListener("WEB", "xpath=//*[@text='Italiano']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.addMobileListener("WEB", "xpath=//*[@text and @class='android.view.View' and @height>0 and ./parent::*[@class='android.view.View' and ./parent::*[@id='vs_cont']]]",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.addMobileListener("WEB", "xpath=//*[@text='Bing' and @class='android.view.View']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.addMobileListener("WEB", "xpath=//*[@text='Wikipedia Wikipedia The Free Encyclopedia']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.addMobileListener("WEB", "xpath=//*[@text='EN']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.addMobileListener("WEB", "xpath=//*[@class='android.widget.Image']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.addMobileListener("WEB", "xpath=//*[@class='android.widget.Image']",
                new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        AutoProject.LOGGER.info(FOUND_MESSAGE + xpath);
                        return true;
                    }
                }
        );
        client.swipeWhileNotFound("down", 100, 1000, "native", "xpath=//*[@text='WebElements']", 0, 1000, 5, false);
        for (int i = 0; i < 10; i++) {
            startTimer();
            client.click("NATIVE", "xpath=//*[@text='WebElements']", 0, 1);
            stopTimer("Click");
            client.sleep(3000);
            client.deviceAction("back");
        }
    }



    @After
    public void tearDown(){
        client.deviceAction("home");
        client.generateReport(false);
        client.releaseClient();
    }

}



