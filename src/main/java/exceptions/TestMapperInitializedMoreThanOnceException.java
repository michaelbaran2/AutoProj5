package exceptions;

public class TestMapperInitializedMoreThanOnceException extends Exception {
    public TestMapperInitializedMoreThanOnceException(String errorMessage) {
        super(errorMessage);
    }

    public TestMapperInitializedMoreThanOnceException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
