package hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces;

import java.util.Set;

/**
 * An automaton transfer function stores all transitions and exposes them to the automaton.
 * 
 * @author Domagoj Polančec
 * @version 1.0
 *
 */
public interface TransferFunction {

    /**
     * Checks if the transfer function contains the given transition.
     * 
     * @param transition
     *            the transition to check
     * @return true if the transition is present in the transfer function, false otherwise
     */
    boolean hasTransition(Transition transition);

    /**
     * Checks if the transfer function contains any transitions such that the given non-null parameters match the
     * corresponding parameters of the transition.<br>
     * 
     * @param oldState
     *            the old state to match
     * @param newState
     *            the new state to match
     * @param input
     *            the input to match
     * @return true if any matching transitions are found, false otherwise
     */
    // TODO: clarify javadoc with an example?
    boolean hasTransition(State oldState, State newState, Input input);

    /**
     * Gets the set of all transitions which are present in the transfer function.
     * 
     * @return the set of all transitions
     */
    Set<Transition> getTransitions();

    /**
     * Gets the set of all transitions which are present in the transfer function for which the given non null
     * parameters match the corresponding parameters in the transition.
     * 
     * @param oldState
     *            the old state to match
     * @param newState
     *            the new state to match
     * @param input
     *            the input to match
     * @return the set of all matching transitions
     */
    // TODO: clarify javadoc with an example?
    Set<Transition> getTransitions(State oldState, State newState, Input input);

    /**
     * Gets the set of new states for the given old state and given input.
     * 
     * @param oldState
     *            the old state
     * @param input
     *            the input
     * @return the set of new states
     */
    Set<State> getNewStates(Set<State> currentStates, Input input);

}
