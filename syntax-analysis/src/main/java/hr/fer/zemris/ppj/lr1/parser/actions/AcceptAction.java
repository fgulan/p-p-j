package hr.fer.zemris.ppj.lr1.parser.actions;

import hr.fer.zemris.ppj.lr1.parser.LR1Parser;

/**
 * <code>RejectAction</code> represents the LR(1) parser action which accepts lexemes.
 *
 * @author Domagoj Polancec
 *
 */
public class AcceptAction implements ParserAction {

    /**
     * Action name.
     */
    public static final String ACTION_NAME = "ACCEPT";

    /**
     * {@inheritDoc}
     *
     * @since alpha
     */
    @Override
    public void execute(LR1Parser parser) {
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        return ACTION_NAME + "()";
    }

    @Override
    public boolean equals(Object o) {
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
