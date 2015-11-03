package hr.fer.zemris.ppj.finite.automaton.generator.interfaces;

import java.util.List;

import hr.fer.zemris.ppj.finite.automaton.interfaces.Automaton;

/**
 * <code>AutomatonGenerator</code> offers set of functions to generate automatons.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public interface AutomatonGenerator {

    /**
     * Generates a automaton from the specified regular expression.
     *
     * @param regularExpression
     *            the expression.
     * @return generated automaton.
     * @since 1.1
     */
    Automaton fromRegularExpression(String regularExpression);

    /**
     * Generates a automaton from textual definition.
     * 
     * @param states
     *            -
     * @param acceptStates
     *            -
     * @param alphabet
     *            -
     * @param transitions
     *            -
     * @param startState
     *            -
     * @return generated automaton
     * @since 1.1
     */
    Automaton fromTextDefinition(String states, String acceptStates, String alphabet, List<String> transitions,
            String startState);
}
