package base.utils.logger;

import base.AutoProject;
import exceptions.LogsFolderWasNotCreatedProperlyException;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class RunLogger {
    public static String NEW_LINE_INDENTATION = "\n\t  ";

    public static void setup() throws IOException, LogsFolderWasNotCreatedProperlyException {
        FileHandler fileTxt;
        SimpleFormatter formatterTxt;

        Logger logger = Logger.getLogger(AutoProject.class.getName());

        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.INFO);
        try {
            fileTxt = new FileHandler("logs" + File.separator + getLogFileNameTimeStamp() + ".log");
        }
        catch (NoSuchFileException e) {
            File logsDir = new File("logs");
            if (logsDir.mkdir()) {
                fileTxt = new FileHandler("logs" + File.separator + getLogFileNameTimeStamp() + ".log");
            }
            else {
                throw new LogsFolderWasNotCreatedProperlyException();
            }
        }
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
    }

    private static String getLogFileNameTimeStamp() {
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH-mm-ss_SSS");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
