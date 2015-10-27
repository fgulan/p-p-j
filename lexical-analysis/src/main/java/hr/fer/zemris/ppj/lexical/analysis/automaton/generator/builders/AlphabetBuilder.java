package hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.lexical.analysis.automaton.BasicInput;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;

/**
 * <code>AlphabetBuilder</code> is a builder for the finite automaton alphabet.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class AlphabetBuilder {

    private final Set<Character> rawInput;

    /**
     * Class constructor.
     *
     * @since 1.0
     */
    public AlphabetBuilder() {
        rawInput = new HashSet<>();
    }

    /**
     * Creates the alphabet of the automaton.
     *
     * @return the alphabet.
     * @since 1.0
     */
    public Set<Input> build() {
        final Set<Input> alphabet = new HashSet<>();
        for (final Character symbol : rawInput) {
            alphabet.add(new BasicInput(symbol));
        }
        return alphabet;
    }

    /**
     * Adds a symbol to the alphabet.
     *
     * @param symbol
     *            the symbol.
     * @since 1.0
     */
    public void addSymbol(final char symbol) {
        rawInput.add(symbol);
    }
}
