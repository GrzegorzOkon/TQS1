package okon.exception;

public class HostConnectionException extends RuntimeException {
    public HostConnectionException(Throwable cause) {
        super(cause);
    }

    public HostConnectionException(String message) {
        super(message);
    }
}
