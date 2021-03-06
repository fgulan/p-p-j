package hr.fer.zemris.ppj.finite.automaton.generator.builders;

import hr.fer.zemris.ppj.finite.automaton.BasicState;
import hr.fer.zemris.ppj.finite.automaton.generator.interfaces.StateBuilder;

/**
 * <code>BasicStateBuilder</code> is a class which builds states of the automaton.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class BasicStateBuilder implements StateBuilder {

    private final String number;
    private boolean acceptance;

    /**
     * Class constructor specifies the state id and the acceptance.
     *
     * @param number
     *            the state id.
     * @param acceptance
     *            the acceptance of the state.
     * @since 1.0
     */
    public BasicStateBuilder(final String number, final boolean acceptance) {
        this.number = number;
        this.acceptance = acceptance;
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.0
     */
    @Override
    public void changeAcceptance(final boolean acceptance) {
        this.acceptance = acceptance;
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.0
     */
    @Override
    public boolean isAccepting() {
        return acceptance;
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.0
     */
    @Override
    public BasicState build() {
        return new BasicState(String.valueOf(number));
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.0
     */
    @Override
    public String getId() {
        return number;
    }
}
