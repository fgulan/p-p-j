package hr.fer.zemris.ppj.lexical.analysis.automaton;

import java.util.Set;

import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transfer.ENFAutomatonTransferFunction;

public class ENFAutomaton extends AbstractAutomaton {

    public ENFAutomaton(Set<State> states, Set<State> acceptStates, Set<Input> inputs,
            ENFAutomatonTransferFunction transferFunction, State startState) {
        super(states, acceptStates, inputs, transferFunction, startState);
    }

    public ENFAutomaton(Set<State> acceptStates, ENFAutomatonTransferFunction transferFunction, State startState) {
        super(acceptStates, transferFunction, startState);
    }

}
