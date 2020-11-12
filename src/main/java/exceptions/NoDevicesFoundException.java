package exceptions;

public class NoDevicesFoundException extends Exception{

    public NoDevicesFoundException(String errorMessage) {
        super(errorMessage);
    }

    public NoDevicesFoundException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
