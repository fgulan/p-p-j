package hr.fer.zemris.ppj.semantic.exceptions;

/**
 * Thrown when a line of code that shouldn't have executed executes (duh).
 * 
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

    public MysteriousBugException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public MysteriousBugException(final String message, final Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public MysteriousBugException(final String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public MysteriousBugException(final Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
