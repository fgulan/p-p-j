package hr.fer.zemris.ppj.finite.automaton.transfer;

import hr.fer.zemris.ppj.finite.automaton.interfaces.State;

/**
 * <code>EpsilonTransition</code> represents a e-move of the finite automaton.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class EpsilonTransition extends FAutomatonTransition {

    /**
     * Class constructor, specifies the old state and the new state of the transition.
     *
     * @param oldState
     *            the old state.
     * @param newState
     *            the new state.
     * @since 1.0
     */
    public EpsilonTransition(final State oldState, final State newState) {
        super(oldState, newState, null);
    }

    @Override
    public boolean isEpsilonTransition() {
        return true;
    }

}
