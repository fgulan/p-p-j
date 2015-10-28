package hr.fer.zemris.ppj.lexical.analysis.analyzer.actions;

/**
 * <code>ActionFactory</code>
 *
 * @author Jan Kelemen
 *
 * @version
 */
public class ActionFactory {

    private static final String NEW_LINE_ACTION = "NOVI_REDAK";
    private static final String RETURN_ACTION = "VRATI_SE";
    private static final String ENTER_STATE_ACTION = "UDJI_U_STANJE";
    private static final String REJECT_ACTION = "-";

    public static LexerAction fromString(final String actionString) {
        String[] splitString = actionString.split(" ");
        switch (splitString[0]) {
            case NEW_LINE_ACTION:
                return new NewLineAction();
            case RETURN_ACTION:
                return new ReturnAction(Integer.valueOf(splitString[1]));
            case ENTER_STATE_ACTION:
                return new EnterStateAction(splitString[1]);
            case REJECT_ACTION:
                return new RejectAction();
            default: // Handles tokens
                return new TokenizeAction(splitString[0]);
        }
    }

}
