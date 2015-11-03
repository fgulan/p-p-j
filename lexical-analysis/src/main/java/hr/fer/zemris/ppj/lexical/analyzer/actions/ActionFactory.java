package hr.fer.zemris.ppj.lexical.analyzer.actions;

/**
 * <code>ActionFactory</code> is a factory for lexical analyzer actions.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class ActionFactory {

    /**
     * Creates a action from the string.
     *
     * @param actionString
     *            the string representation of the action.
     * @return created action.
     * @since 1.0
     */
    public static LexerAction fromString(final String actionString) {
        final String[] splitString = actionString.split(" ");
        switch (splitString[0]) {
            case NewLineAction.ACTION_STRING:
                return new NewLineAction();
            case ReturnAction.ACTION_STRING:
                return new ReturnAction(Integer.valueOf(splitString[1]));
            case EnterStateAction.ACTION_STRING:
                return new EnterStateAction(splitString[1]);
            case RejectAction.ACTION_STRING:
                return new RejectAction();
            default: // Handles tokens
                return new TokenizeAction(splitString[0]);
        }
    }

}
