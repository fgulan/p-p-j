package hr.fer.zemris.ppj.lr1.parser.actions;

import hr.fer.zemris.ppj.lr1.parser.LR1Parser;

/**
 * <code>RejectAction</code> represents a action of the LR(1) parser which rejects lexemes.
 *
 * @author Jan Kelemen
 * @author Matea Sabolic
 *
 * @version alpha
 */
public class RejectAction implements ParserAction {

    /**
     * Action name.
     */
    public static final String ACTION_NAME = "REJECT";

    /**
     * {@inheritDoc}
     *
     * @since alpha
     */
    @Override
    public void execute(final LR1Parser parser) {
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        return ACTION_NAME + "()";
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof RejectAction)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
