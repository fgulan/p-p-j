package hr.fer.zemris.ppj.semantic.exceptions;

/**
 * Thrown when a line of code that shouldn't have executed executes (duh).
 * @author Domagoj Polancec
 *
 */
public class MysteriousBugException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MysteriousBugException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MysteriousBugException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public MysteriousBugException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public MysteriousBugException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public MysteriousBugException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
    
    
}
