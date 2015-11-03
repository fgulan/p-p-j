package hr.fer.zemris.ppj.finite.automaton.generator.interfaces;

import java.util.Map;

import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.interfaces.TransferFunction;

/**
 * <code>TransferFunctionBuilder</code> is a builder for automaton transfer function.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public interface TransferFunctionBuilder {

    /**
     * Builds the transfer function.
     *
     * @param states
     *            states of the automaton
     * @return the transfer function.
     */
    TransferFunction build(Map<String, State> states);

    /**
     * Adds a transition to the transition function.
     *
     * @param oldState
     *            id of the original state of the transition.
     * @param symbol
     *            symbol of the transition.
     * @param newState
     *            id of the new state of the transition.
     */
    void addTransition(String oldState, Character symbol, String newState);
}
