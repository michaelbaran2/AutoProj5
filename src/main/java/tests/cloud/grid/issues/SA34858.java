package tests.cloud.grid.issues;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;



public class SA34858 extends BaseTest {
    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp(){
        grid = new GridClient(this.accessKey, this.url);
        client = grid.lockDeviceForExecution("Workaround Test", "@serialNumber='" + this.deviceSN + "'", 10, 50000);
        client.setReporter("xml", "", "Workaround Test");
        client.install(APK_FOLDER_PATH + "sa34858.apk", false, false);
        client.launch("com.ticktick.task/.activity.MeTaskActivity", false, true);
    }

    @Test
    public void workaroundTest() {
        client.sleep(3000);
        client.click("NATIVE", "//*[@text='NEW ONBOARD']", 0, 1);
        client.click("NATIVE", "//*[@text='SKIP']", 0, 1);
        client.setDragStartDelay(1000);
        client.dragDrop("NATIVE", "//*[@id='a2b' and ./parent::*[@class='android.widget.FrameLayout']]/*[@class='android.view.View' and @width>0]", 2, "//*[@id='a2b' and ./parent::*[@class='android.widget.FrameLayout']]/*[@class='android.view.View' and @width>0]", 1);
        client.sleep(3000);
        client.setDragStartDelay(0);
        client.elementSwipe("NATIVE", "//*[@id='a2b' and ./parent::*[@class='android.widget.FrameLayout']]/*[@class='android.view.View' and @width>0]", 1, "Right", 300, 1000);



    }

    @After
    public void tearDown(){
        client.generateReport(false);
        client.releaseClient();
    }
}
