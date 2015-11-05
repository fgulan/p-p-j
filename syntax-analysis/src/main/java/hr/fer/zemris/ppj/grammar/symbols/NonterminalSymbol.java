package hr.fer.zemris.ppj.grammar.symbols;

import hr.fer.zemris.ppj.grammar.interfaces.Symbol;

/**
 * <code>NonterminalSymbol</code> represents a nonterminal symbol of the context free grammar.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class NonterminalSymbol implements Symbol {

    private final String name;

    /**
     * Class constructor, specifies the name of the symbol.
     *
     * @param name
     *            the name.
     * @since alpha
     */
    public NonterminalSymbol(final String name) {
        this.name = name;
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

    /**
     * {@inheritDoc}
     *
     * @since alpha
     */
    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if ((o instanceof String) || (o instanceof NonterminalSymbol)) {
            return name.equals(((NonterminalSymbol) o).name);
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

}
