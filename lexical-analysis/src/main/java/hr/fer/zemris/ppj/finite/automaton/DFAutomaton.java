package hr.fer.zemris.ppj.finite.automaton;

import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.finite.automaton.transfer.DFAutomatonTransferFunction;

/**
 * <code>DFAutomaton</code> is a deterministic finite automaton.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class DFAutomaton extends AbstractAutomaton {

    /**
     * Class constructor, specifies formal definition of automaton.
     *
     * @param states
     *            the states of the automaton
     * @param acceptStates
     *            the accepting states of the automaton.
     * @param alphabet
     *            the alphabet of the automaton.
     * @param transferFunction
     *            the transfer function of the automaton.
     * @param startState
     *            the initial state of the automaton.
     * @since 1.0
     */
    public DFAutomaton(final Set<State> states, final Set<State> acceptStates, final Set<Input> alphabet,
            final DFAutomatonTransferFunction transferFunction, final State startState) {
        super(states, acceptStates, alphabet, transferFunction, startState);
    }

    /**
     * Returns the current state of the automaton.
     *
     * @return the current state.
     * @since 1.0
     */
    public State getCurrentState() {
        final Set<State> currentStates = getCurrentStates();
        if (currentStates.isEmpty()) {
            return null;
        }
        else {
            return currentStates.iterator().next();
        }
    }

    /**
     * Returns the transition of the automaton from the specified state for the given input.
     *
     * @param state
     *            the specified state.
     * @param input
     *            the input.
     * @return the transition.
     * @since 1.0
     */
    public Transition getTransition(final State state, final Input input) {
        final Set<Transition> transitions = getTransferFunction().getTransitions(state, null, input);
        if (transitions.isEmpty()) {
            return null;
        }
        else {
            return transitions.iterator().next();
        }
    }
}
