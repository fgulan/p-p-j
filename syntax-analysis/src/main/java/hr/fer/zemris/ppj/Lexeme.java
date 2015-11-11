package hr.fer.zemris.ppj;

import hr.fer.zemris.ppj.grammar.interfaces.Symbol;

/**
 * <code>Lexeme</code> represents a lexical unit.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class Lexeme {

    private final String type;
    private final int lineNumber;
    private final Symbol value;

    /**
     * Class constructor, specifies the lexeme.
     *
     * @param type
     *            the type of the lexeme.
     * @param lineNumber
     *            the line number at which a lexeme appears in the source code.
     * @param value
     *            the value of the lexeme.
     * @since 1.0
     */
    public Lexeme(final String type, final int lineNumber, final Symbol value) {
        this.type = type;
        this.lineNumber = lineNumber;
        this.value = value;
    }

    /**
     * @return the type of the lexeme.
     * @since 1.0
     */
    public String type() {
        return type;
    }

    /**
     * @return line number at which the lexeme was found in the source code.
     * @since 1.0
     */
    public int lineNumber() {
        return lineNumber;
    }

    /**
     * @return value of the lexeme.
     * @since 1.0
     */
    public Symbol value() {
        return value;
    }

    @Override
    public String toString() {
        return type + " " + lineNumber + " " + value.toString();
    }
}
