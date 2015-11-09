package hr.fer.zemris.ppj.lr1.parser.actions;

import hr.fer.zemris.ppj.lr1.parser.LR1Parser;

/**
 * <code>ShiftAction</code> represents a shift action of the LR(1) parser.
 *
 * @author Jan Kelemen
 * @author Matea Sabolic
 *
 * @version alpha
 */
public class ShiftAction implements ParserAction {

    /**
     * Action name.
     */
    public static final String ACTION_NAME = "SHIFT";

    private final String stateID;

    /**
     * Class constructor, specifies the new state on the top of the stack.
     *
     * @param stateID
     *            the state ID.
     * @since alpha
     */
    public ShiftAction(final String stateID) {
        this.stateID = stateID;
    }

    /**
     * {@inheritDoc}
     *
     * @since alpha
     */
    @Override
    public void execute(LR1Parser parser) {
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
    public boolean equals(Object o) {
        if (!(o instanceof ShiftAction)) {
            return false;
        }

        ShiftAction action = (ShiftAction) o;
        return stateID.equals(action.stateID());
    }

    @Override
    public int hashCode() {
        return stateID.hashCode();
    }

}
