package hr.fer.zemris.ppj.lexical.analysis.automaton.transfer;

import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;

public class NormalTransition extends FAutomatonTransition {

    public NormalTransition(State oldState, State newState, Input input) {
        super(oldState, newState, input);
    }

    @Override
    public boolean isEpsilonTransition() {
        return false;
    }
}
