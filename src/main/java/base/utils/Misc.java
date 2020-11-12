package base.utils;

import exceptions.NoDevicesFoundException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Misc {

    public static HashMap<String, String> createThreadDeviceMapping(String[] deviceSNs, Thread[] threads) {

        HashMap<String, String> threadDeviceMapping = new HashMap<>();
        for (int i = 0; i < deviceSNs.length; i++) {
            threadDeviceMapping.put(threads[i].getName(), deviceSNs[i]);
        }
        return threadDeviceMapping;
    }

    public static String getTimeStamp() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
