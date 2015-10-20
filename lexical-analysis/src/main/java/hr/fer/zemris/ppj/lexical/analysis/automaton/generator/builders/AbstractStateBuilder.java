package hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders;

import hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.interfaces.StateBuilder;

/**
 * <code>AbstractStateBuilder</code> is a abstract class which implements behavior common to all state builders.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public abstract class AbstractStateBuilder implements StateBuilder {

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
    public AbstractStateBuilder(final String number, final boolean acceptance) {
        this.number = number;
        this.acceptance = acceptance;
    }

    /**
     * {@inheritDoc}
     *
     * @see hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.interfaces.StateBuilder#changeAcceptance(boolean)
     * @since 1.0
     */
    @Override
    public void changeAcceptance(final boolean acceptance) {
        this.acceptance = acceptance;
    }

    /**
     * {@inheritDoc}
     *
     * @see hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.interfaces.StateBuilder#isAccepting()
     * @since 1.0
     */
    @Override
    public boolean isAccepting() {
        return acceptance;
    }

    /**
     * {@inheritDoc}
     *
     * @see hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.interfaces.StateBuilder#getId()
     * @since 1.0
     */
    @Override
    public String getId() {
        return number;
    }
}
