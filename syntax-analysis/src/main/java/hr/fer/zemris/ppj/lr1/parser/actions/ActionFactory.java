package hr.fer.zemris.ppj.lr1.parser.actions;

import hr.fer.zemris.ppj.grammar.ProductionParser;

/**
 * <code>ActionFactory</code> is a factory for LR(1) parser actions.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class ActionFactory {

    /**
     * Creates a action from the the string.
     *
     * @param definition
     *            the action definiton.
     * @return created action.
     * @throws IllegalArgumentException
     *             If the specified action isn't defined.
     * @since alpha
     */
    public static ParserAction fromString(final String definition) {
        String actionName = definition.substring(0, definition.indexOf('('));
        String argument = definition.substring(definition.indexOf('(') + 1, definition.length() - 1);

        switch (actionName) {
            case ShiftAction.ACTION_NAME:
                return new ShiftAction(argument);
            case ReduceAction.ACTION_NAME:
                return new ReduceAction(ProductionParser.fromText(argument));
            case PutAction.ACTION_NAME:
                return new PutAction(argument);
            case RejectAction.ACTION_NAME:
                return new RejectAction();
            case AcceptAction.ACTION_NAME:
                return new AcceptAction();
            default:
                throw new IllegalArgumentException("Undefined action: " + definition + ".");
        }
    }

}
