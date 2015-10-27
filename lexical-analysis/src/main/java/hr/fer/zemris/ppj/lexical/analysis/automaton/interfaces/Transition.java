package hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces;

/**
 * Represents a transition in an automaton's transfer function. Transitions are defined with three parameters: old
 * state, new state and input. Old state is the automaton's state before receiving the input and new state is the
 * automaton's state after receiving the input.
 *
 * @author Domagoj Polancec
 * @version 1.0
 *
 */
public interface Transition {

    /**
     * Gets the old state.
     *
     * @return the old state
     */
    State getOldState();

    /**
     * Gets the new state.
     *
     * @return the new state
     */
    State getNewState();

    /**
     * Gets the input.
     *
     * @return the input
     */
    Input getInput();
}
