package tests;

import base.AutoProject;

public class MeasuringTest extends BaseTest {
    protected long start;
    protected long end;

    public void startTimer() {
        start = System.currentTimeMillis();
    }

    /**
     *
     * @param measuredAction Description for what action was measured, for logging purposes.
     */
    public void stopTimer(String measuredAction) {
        end = System.currentTimeMillis();
        double elapsedSeconds = (double)(end - start) / 1000;
        AutoProject.LOGGER.info(measuredAction + " elapsed " + elapsedSeconds + " seconds");
        if (AutoProject.runProperties.requiresMeasureTiming()) {
            AutoProject.timeMeasurements.add(elapsedSeconds);
        }
    }

}
