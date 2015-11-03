package hr.fer.zemris.ppj.finite.automaton.transfer;

import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;

/**
 * <code>DeterministicTransition</code> represents a deterministic transition of the finite automaton.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class NormalTransition extends FAutomatonTransition {

    /**
     * Class constructor, specifies the old state, the new state and the input of the transition.
     *
     * @param oldState
     *            the old state.
     * @param newState
     *            the new state.
     * @param input
     *            the input.
     * @since 1.0
     */
    public NormalTransition(final State oldState, final State newState, final Input input) {
        super(oldState, newState, input);
    }

    @Override
    public boolean isEpsilonTransition() {
        return false;
    }
}
