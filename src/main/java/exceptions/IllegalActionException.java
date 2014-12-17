package exceptions;

/**
 * Exception thrown when an action could not be thrown.
 */
public class IllegalActionException extends IllegalStateException {

    /**
     * {@inheritDoc}
     */
    public IllegalActionException() {
    }

    /**
     * {@inheritDoc}
     */
    public IllegalActionException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public IllegalActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
