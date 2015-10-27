package hr.fer.zemris.ppj.lexical.analysis.automaton.transfer;

import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;

public class EpsilonTransition extends FAutomatonTransition {

    public EpsilonTransition(final State oldState, final State newState) {
        super(oldState, newState, null);
    }

    @Override
    public boolean isEpsilonTransition() {
        return true;
    }

}
