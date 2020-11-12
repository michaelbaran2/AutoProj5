package tests.cloud.grid;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;

public class InstallSanityTest extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;
    protected String titleXpath = "//*[@class='android.widget.ImageView' and ./parent::*[@id='makePaymentView']]";

    @Before
    public void setUp(){
        grid = new GridClient(this.accessKey, this.url);
    }

    @Test
    public void installInstrumentedFromPath() {
        client = grid.lockDeviceForExecution("Install instrumented from path test " + this.deviceSN, "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Install instrumented from path test");
        client.install("resources\\apk\\eribank.apk", true, false);
//        client.install("resources\\apk\\erank.apk", true, false);
//        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
//        Assert.assertTrue("App wasn't launched, title wasn't found on screen", client.isElementFound("NATIVE", titleXpath));
    }

    @Test
    public void installNonInstrumentedFromPath() {
        client = grid.lockDeviceForExecution("Install non-instrumented from path test " + this.deviceSN, "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Install non-instrumented from path test");
        client.install("resources\\apk\\eribank.apk", false, false);
//        client.launch("com.experitest.ExperiBank/.LoginActivity", false, true);
//        Assert.assertTrue("App wasn't launched, title wasn't found on screen", client.isElementFound("NATIVE", titleXpath));
    }

    @Test
    public void installInstrumentedFromAppManager() {
        client = grid.lockDeviceForExecution("Install instrumented from app manager test " + this.deviceSN, "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Install instrumented from app manager test");
        client.install("cloud:com.experitest.ExperiBank", true, false);
//        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
//        Assert.assertTrue("App wasn't launched, title wasn't found on screen", client.isElementFound("NATIVE", titleXpath));
    }

    @Test
    public void installNonInstrumentedFromAppManager() {
        client = grid.lockDeviceForExecution("Install non-instrumented from app manager test " + this.deviceSN, "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Install non-instrumented from app manager test");
        client.install("cloud:com.experitest.ExperiBank", false, false);
//        client.launch("com.experitest.ExperiBank/.LoginActivity", false, true);
//        Assert.assertTrue("App wasn't launched, title wasn't found on screen", client.isElementFound("NATIVE", titleXpath));
    }

    @After
    public void tearDown(){
        client.uninstall("com.experitest.ExperiBank/.LoginActivity");
        client.generateReport(false);
        client.releaseClient();
    }
}
