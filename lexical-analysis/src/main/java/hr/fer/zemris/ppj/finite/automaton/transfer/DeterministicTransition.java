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
public class DeterministicTransition extends NormalTransition {

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
    public DeterministicTransition(final State oldState, final State newState, final Input input) {
        super(oldState, newState, input);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final FAutomatonTransition other = (FAutomatonTransition) obj;
        if (getInput() == null) {
            if (other.getInput() != null) {
                return false;
            }
        }
        else if (!getInput().equals(other.getInput())) {
            return false;
        }
        if (getOldState() == null) {
            if (other.getOldState() != null) {
                return false;
            }
        }
        else if (!getOldState().equals(other.getOldState())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((getInput() == null) ? 0 : getInput().hashCode());
        result = (prime * result) + ((getOldState() == null) ? 0 : getOldState().hashCode());
        return result;
    }

}
