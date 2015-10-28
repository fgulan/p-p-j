package hr.fer.zemris.ppj.lexical.analyzer.actions;

/**
 * <code>ActionFactory</code>
 *
 * @author Jan Kelemen
 *
 * @version
 */
public class ActionFactory {

    public static LexerAction fromString(final String actionString) {
        String[] splitString = actionString.split(" ");
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
