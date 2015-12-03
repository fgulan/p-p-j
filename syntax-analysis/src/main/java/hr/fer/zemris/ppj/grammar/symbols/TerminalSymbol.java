package hr.fer.zemris.ppj.grammar.symbols;

import hr.fer.zemris.ppj.grammar.interfaces.Symbol;

/**
 * <code>TerminalSymbol</code> represents a terminal symbol of the context free grammar.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class TerminalSymbol implements Symbol {

    private final String name;

    /**
     * Class constructor, specifies the name of the symbol.
     *
     * @param name
     *            the name.
     * @since alpha
     */
    public TerminalSymbol(final String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     *
     * @since alpha
     */
    @Override
    public boolean isTerminal() {
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @since alpha
     */
    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if ((o instanceof String) || (o instanceof TerminalSymbol)) {
            return name.equals(o.toString());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(final Symbol o) {
        if (o instanceof NonterminalSymbol) {
            return 1;
        }
        return name.compareTo(o.name());
    }

}
