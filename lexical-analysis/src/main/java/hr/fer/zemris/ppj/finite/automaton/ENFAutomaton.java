package hr.fer.zemris.ppj.finite.automaton;

import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.transfer.ENFAutomatonTransferFunction;

/**
 * <code>ENFAutomaton</code> is a nondeterministic finite automaton with e-moves.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class ENFAutomaton extends AbstractAutomaton {

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
    public ENFAutomaton(final Set<State> states, final Set<State> acceptStates, final Set<Input> alphabet,
            final ENFAutomatonTransferFunction transferFunction, final State startState) {
        super(states, acceptStates, alphabet, transferFunction, startState);
    }

}
