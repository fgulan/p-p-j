package hr.fer.zemris.ppj.lexical.analysis.automaton;

import java.util.Set;

import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transfer.DFAutomatonTransferFunction;

public class DFAutomaton extends AbstractAutomaton {

    public DFAutomaton(Set<State> states, Set<State> acceptStates, Set<Input> inputs,
            DFAutomatonTransferFunction transferFunction, State startState) {
        super(states, acceptStates, inputs, transferFunction, startState);
    }

    public DFAutomaton(Set<State> acceptStates, DFAutomatonTransferFunction transferFunction, State startState) {
        super(acceptStates, transferFunction, startState);
    }

    public State getCurrentState() {
        Set<State> currentStates = getCurrentStates();
        if (currentStates.isEmpty()) {
            return null;
        }
        else {
            return currentStates.iterator().next();
        }
    }

    public Transition getTransition(State oldState, Input input) {
        Set<Transition> transitions = getTransferFunction().getTransitions(oldState, null, input);
        if (transitions.isEmpty()) {
            return null;
        }
        else {
            return transitions.iterator().next();
        }
    }
}
