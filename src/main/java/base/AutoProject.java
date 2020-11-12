package base;

import base.utils.AtomicCounter;
import base.utils.Misc;
import base.utils.logger.RunLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;


public class AutoProject {
    public final static Logger LOGGER = Logger.getLogger(AutoProject.class.getName());
    public static TestMapper testMapper;
    public static HashMap<String, String> threadDeviceMapping;
    public static RunProperties runProperties;
    public static AtomicCounter testsPassed = new AtomicCounter(0);
    public static AtomicCounter testsFailed = new AtomicCounter(0);
    public static List<Object> timeMeasurements;

    public static void main(String[] args) {
        try {
            RunLogger.setup();
            LOGGER.info("Starting test suite.");
            testMapper  = TestMapper.initTestMapper();
            runProperties = RunProperties.fetchRunProperties(testMapper);
            if (runProperties.requiresMeasureTiming()) {
                timeMeasurements = Collections.synchronizedList(new ArrayList<>());
                String beforeTimingMessage = "Will take timing measurement. Make sure the all measuring tests are timing the same action. Tests measuring the same action will be in the same package.";
                System.out.println(beforeTimingMessage);
                LOGGER.info(beforeTimingMessage);
            }
            String iterationsMessage = "Suite will run for " + runProperties.getIterations() + " iteration";
            if (runProperties.getIterations() == 1) {
                System.out.println(iterationsMessage);
                LOGGER.info(iterationsMessage);
            }
            else {
                System.out.println(iterationsMessage + "s");
                LOGGER.info(iterationsMessage + "s");
            }
            for (int i = 0; i < runProperties.iterations; i++) {
                if (runProperties.getIterations() > 1) {
                    String iterationStarting = "Iteration " + (i + 1) + " starting...";
                    System.out.println(iterationStarting);
                    LOGGER.info(iterationStarting);
                }
                iteration();
            }
            LOGGER.info("Run finished. Total tests ran: " + (testsFailed.getValue() + testsPassed.getValue())
                    + RunLogger.NEW_LINE_INDENTATION + "Passed tests: " + testsPassed.getValue()
                    + RunLogger.NEW_LINE_INDENTATION + "Failed tests: " + testsFailed.getValue());
            if (runProperties.requiresMeasureTiming()) {
                if (timeMeasurements.size() == 0) {
                    System.out.println("No time measurements were taken");
                    LOGGER.info("No time measurements were taken");
                }
                else {
                    double sum = 0;
                    for (Object t: timeMeasurements) {
                        sum += (double)t;
                    }
                    double avg = sum / timeMeasurements.size();
                    LOGGER.info("Average time measured: " + avg + " seconds" + RunLogger.NEW_LINE_INDENTATION + "Average was taken over " + timeMeasurements.size() + " measurements");
                    System.out.println("Average time measured: " + avg + " seconds");
                    System.out.println("Average was taken over " + timeMeasurements.size() + " measurements");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void iteration() throws InterruptedException {
        String suiteStartInfo = "Iteration starting on " + runProperties.getUrl() + " on " + runProperties.getDeviceSNs().length + " available devices.";
        System.out.println(suiteStartInfo);
        LOGGER.info(suiteStartInfo);
        Thread[] threads = new Thread[runProperties.getDeviceSNs().length];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new TesterThread());
        }
        threadDeviceMapping = Misc.createThreadDeviceMapping(runProperties.getDeviceSNs(), threads);
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread: threads) {
            thread.join();
        }
    }
}
