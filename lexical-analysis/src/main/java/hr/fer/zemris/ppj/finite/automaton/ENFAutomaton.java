package hr.fer.zemris.ppj.finite.automaton;

import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.transfer.ENFAutomatonTransferFunction;

public class ENFAutomaton extends AbstractAutomaton {

    public ENFAutomaton(final Set<State> states, final Set<State> acceptStates, final Set<Input> alphabet,
            final ENFAutomatonTransferFunction transferFunction, final State startState) {
        super(states, acceptStates, alphabet, transferFunction, startState);
    }

    public ENFAutomaton(final Set<State> acceptStates, final ENFAutomatonTransferFunction transferFunction,
            final State startState) {
        super(acceptStates, transferFunction, startState);
    }

}