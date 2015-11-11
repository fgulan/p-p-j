package hr.fer.zemris.ppj.lr1.parser.actions;

import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.lr1.parser.LR1Parser;

/**
 * <code>ReduceAction</code> represents a reduce action of the LR(1) parser.
 *
 * @author Jan Kelemen
 * @author Matea Sabolic
 *
 * @version alpha
 */
public class ReduceAction implements ParserAction {

    /**
     * Action name.
     */
    public static final String ACTION_NAME = "REDUCE";

    private final Production production;

    /**
     * Class constructor, specifies the production by which a input sequence is reduced.
     *
     * @param production
     *            the production.
     * @since alpha
     */
    public ReduceAction(final Production production) {
        this.production = production;
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
     * @return the production of the action.
     */
    public Production production() {
        return production;
    }

    @Override
    public String toString() {
        return ACTION_NAME + "(" + production.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReduceAction)) {
            return false;
        }

        ReduceAction action = (ReduceAction) o;
        return production.equals(action.production());
    }

    @Override
    public int hashCode() {
        return production.hashCode();
    }

}
