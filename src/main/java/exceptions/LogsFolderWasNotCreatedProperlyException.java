package exceptions;

public class LogsFolderWasNotCreatedProperlyException extends Exception{


    public LogsFolderWasNotCreatedProperlyException(String errorMessage) {
        super(errorMessage);
    }

    public LogsFolderWasNotCreatedProperlyException() {
        super("Logs folder wasn't create successfully.");
    }

    public LogsFolderWasNotCreatedProperlyException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
