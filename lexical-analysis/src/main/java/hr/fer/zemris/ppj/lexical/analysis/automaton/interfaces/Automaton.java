package hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces;

import java.util.Set;

/**
 * An automaton is a construct that receives an input and changes its current state(s) depending on the transition(s)
 * defined in the automaton's transfer function.<br>
 * <br>
 *
 * Each automaton is defined by at least the following: start state, set of defined states, set of valid inputs,
 * transfer function and the set of accept states.
 *
 * @author Domagoj Polancec
 * @version 1.0
 * @see Input
 * @see State
 * @see Transition
 * @see TransferFunction
 */
public interface Automaton {

    /**
     * Receives the input and updates the automaton's current state(s).
     *
     * @param input
     *            the input to receive
     */
    void apply(Input input);

    /**
     * Gets the last received input. This is null if and only if no inputs have been received by the automaton.
     *
     * @return the last received input
     */
    Input getLastInput();

    /**
     * Gets the set of the automaton's current states. This method will never return null. If the automaton has no
     * current states, an empty set is returned.
     *
     * @return the set of current states
     */
    Set<State> getCurrentStates();

    /**
     * Gets the set of all states defined for the automaton. This method will never return null.
     *
     * @return the set of all states defined for the automaton
     */
    Set<State> getStates();

    /**
     * Gets the set of the automaton's accept states. This method will never return null. If the automaton has no accept
     * states, an empty set is returned.
     *
     * @return the set of accept states
     */
    Set<State> getAcceptStates();

    /**
     * Gets the automaton's start state.
     *
     * @return the start state
     */
    State getStartState();

    /**
     * Gets the automaton's transfer function.
     *
     * @return the transfer function
     */
    TransferFunction getTransferFunction();

    /**
     * Checks if the given state is one of the automaton's accept states.
     *
     * @param state
     *            the state to check
     * @return true if the state is one of the automaton's accept states, false otherwise
     */
    boolean isAcceptState(State state);

    /**
     * Checks if the given state is one of the automaton's current states.
     *
     * @param state
     *            the state to check
     * @return true if the state is one of the automaton's current states, false otherwise
     */
    boolean isCurrentState(State state);

    /**
     * Checks if the given state is one of the automaton's states.
     *
     * @param state
     *            the state to check
     * @return true if the state is one of the automaton's states, false otherwise
     */
    boolean hasState(State state);

    /**
     * Gets the set of the defined inputs for the automaton.
     *
     * @return the set of the defined inputs
     */
    Set<Input> getInputs();

    void reset();

    boolean isAccepting();
}
