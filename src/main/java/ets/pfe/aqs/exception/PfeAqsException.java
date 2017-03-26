package ets.pfe.aqs.exception;

/**
 * 
 * @author Zeldorine
 */
public class PfeAqsException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 2368224991685798294L;

    /**
     * 
     */
    public PfeAqsException() {
        super();
    }

    /**
     * 
     * @param message
     * @param cause 
     */
    public PfeAqsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     * @param message 
     */
    public PfeAqsException(String message) {
        super(message);
    }

    /**
     * 
     * @param cause 
     */
    public PfeAqsException(Throwable cause) {
        super(cause);
    }

}
