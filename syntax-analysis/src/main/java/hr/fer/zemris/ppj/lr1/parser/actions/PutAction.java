package hr.fer.zemris.ppj.lr1.parser.actions;

import hr.fer.zemris.ppj.lr1.parser.LR1Parser;

/**
 * <code>PutAction</code> represents a action which changes the state of the LR(1) parser.
 *
 * @author Jan Kelemen
 * @author Matea Sabolic
 *
 * @version alpha
 */
public class PutAction implements ParserAction {

    /**
     * Action name.
     */
    public static final String ACTION_NAME = "PUT";

    private final String stateID;

    /**
     * Class constructor, specifies the new state on the top of the stack.
     *
     * @param stateID
     *            the state ID.
     * @since alpha
     */
    public PutAction(final String stateID) {
        this.stateID = stateID;
    }

    /**
     * {@inheritDoc}
     *
     * @since alpha
     */
    @Override
    public void execute(final LR1Parser parser) {
        // TODO Auto-generated method stub

    }

    /**
     * @return the new state on the top of the stack, after the execution of the action.
     * @since alpha
     */
    public String stateID() {
        return stateID;
    }

    @Override
    public String toString() {
        return ACTION_NAME + "(" + stateID + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof PutAction)) {
            return false;
        }

        final PutAction action = (PutAction) o;
        return stateID.equals(action.stateID());
    }

    @Override
    public int hashCode() {
        return stateID.hashCode();
    }

}
