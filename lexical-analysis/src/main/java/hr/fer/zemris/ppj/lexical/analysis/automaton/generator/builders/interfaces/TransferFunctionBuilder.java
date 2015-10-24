package hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.interfaces;

import java.util.Map;

import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.TransferFunction;

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
     * @since 1.0
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
     * @since 1.0
     */
    void addTransition(String oldState, Character symbol, String newState);
}
