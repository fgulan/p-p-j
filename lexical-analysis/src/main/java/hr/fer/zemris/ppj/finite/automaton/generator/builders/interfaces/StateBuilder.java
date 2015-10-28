package hr.fer.zemris.ppj.finite.automaton.generator.builders.interfaces;

import hr.fer.zemris.ppj.finite.automaton.interfaces.State;

/**
 * <code>StateBuilder</code> is a builder for automaton states.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public interface StateBuilder {

    /**
     * Creates the state.
     *
     * @return the state.
     * @since 1.0
     */
    State build();

    /**
     * Changes the acceptance of the state, to the specified value.
     *
     * @param acceptance
     *            new acceptance.
     * @since 1.0
     */
    void changeAcceptance(final boolean acceptance);

    /**
     * Checks if a state is a accepting state.
     *
     * @return acceptance of the state.
     * @since 1.0
     */
    boolean isAccepting();

    /**
     * Returns the state number.
     *
     * @return the state number.
     * @since 1.0
     */
    String getId();
}
