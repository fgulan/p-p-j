package hr.fer.zemris.ppj.finite.automaton.generator.interfaces;

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
     * @since 1.0
     */
    Automaton fromRegularExpression(String regularExpression);
}
