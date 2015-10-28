package hr.fer.zemris.ppj.finite.automaton;

import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.finite.automaton.transfer.DFAutomatonTransferFunction;

public class DFAutomaton extends AbstractAutomaton {

    public DFAutomaton(final Set<State> states, final Set<State> acceptStates, final Set<Input> inputs,
            final DFAutomatonTransferFunction transferFunction, final State startState) {
        super(states, acceptStates, inputs, transferFunction, startState);
    }

    public DFAutomaton(final Set<State> acceptStates, final DFAutomatonTransferFunction transferFunction,
            final State startState) {
        super(acceptStates, transferFunction, startState);
    }

    public State getCurrentState() {
        final Set<State> currentStates = getCurrentStates();
        if (currentStates.isEmpty()) {
            return null;
        }
        else {
            return currentStates.iterator().next();
        }
    }

    public Transition getTransition(final State oldState, final Input input) {
        final Set<Transition> transitions = getTransferFunction().getTransitions(oldState, null, input);
        if (transitions.isEmpty()) {
            return null;
        }
        else {
            return transitions.iterator().next();
        }
    }
}
