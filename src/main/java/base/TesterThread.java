package base;

import base.utils.logger.RunLogger;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.List;

public class TesterThread implements Runnable {
    int passed = 0;
    int failed = 0;
    String deviceSN;

    @Override
    public void run() {
        this.deviceSN = AutoProject.threadDeviceMapping.get(Thread.currentThread().getName());
        Result result;
        int retries;
        for (String testClass: AutoProject.runProperties.getTestClasses()) {
            retries = 0;
            result = JUnitCore.runClasses(AutoProject.testMapper.getTest(testClass));
            while (AutoProject.runProperties.shouldRetryAfterFail() && retries < AutoProject.runProperties.getRetries() && !result.wasSuccessful()) {
                AutoProject.LOGGER.info(this.parseFailures(result.getFailures(), retries));
                retries++;
                result = JUnitCore.runClasses(AutoProject.testMapper.getTest(testClass));
            }
            updateResultCounters(result);
            if (!result.wasSuccessful()) {
                AutoProject.LOGGER.info(this.parseFailures(result.getFailures(), retries));
            }
        }
        AutoProject.LOGGER.info("Overall results for device: " + this.deviceSN
                + RunLogger.NEW_LINE_INDENTATION + "Passed tests: " + this.passed
                + RunLogger.NEW_LINE_INDENTATION + "Failed tests: " + this.failed);
    }

    private String parseFailures(List<Failure> failures, int retry) {
        String[] retryFormats = {
                "",
                "first retry",
                "second retry"
        };
        String tryString = retryFormats[retry];
        StringBuilder output = new StringBuilder("Device: " + this.deviceSN + ", " + tryString + " result: FAILED");
        for (Failure failure: failures) {
            output.append("\n").append(failure.getTestHeader()).append("\n").append(failure.getTrace());
        }
        return output.toString();
    }

    private void updateResultCounters(Result result) {
//        System.out.println(Thread.currentThread().getName() + ": " + "result.getFailureCount() = " + result.getFailureCount() + ", result.getRunCount() = " + result.getRunCount());
        this.failed += result.getFailureCount();
        this.passed += (result.getRunCount() - result.getFailureCount());
//        AutoProject.testsFailed.increment(this.failed);
//        AutoProject.testsPassed.increment(this.passed);
        AutoProject.testsFailed.increment(result.getFailureCount());
        AutoProject.testsPassed.increment(result.getRunCount() - result.getFailureCount());
    }
}
