package hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces;

public interface Transition {

    State getOldState();

    State getNextState();

    Input getInput();
}
