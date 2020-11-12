package exceptions;

public class TestNotFoundException extends Exception {

    public TestNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public TestNotFoundException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
