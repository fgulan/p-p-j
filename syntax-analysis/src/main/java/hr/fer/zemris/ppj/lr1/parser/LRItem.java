package hr.fer.zemris.ppj.lr1.parser;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.grammar.interfaces.Symbol;
import hr.fer.zemris.ppj.grammar.symbols.TerminalSymbol;

/**
 * <code>LRItem</code> represents a LR or LR(1) item.
 *
 * @author Filip Gulan
 *
 * @version 1.0
 */
public class LRItem implements Comparable<LRItem> {

    private final Production production;
    private final Set<Symbol> terminalSymbols;
    private int dotIndex;

    /**
     * Class constructor, specifies the production, the position of the marker and the follows set of the item.
     *
     * @param production
     *            the production.
     * @param dotIndex
     *            the position of the marker.
     * @param terminalSymbols
     *            the follows set of the item.
     * @since 1.0
     */
    public LRItem(final Production production, final int dotIndex, final Set<Symbol> terminalSymbols) {
        super();
        this.production = production;
        this.dotIndex = dotIndex;
        this.terminalSymbols = terminalSymbols;
    }

    /**
     * Creates all LR items from production.
     *
     * @param production
     *            the production.
     * @return created LR items.
     * @since 1.0
     */
    public static Set<LRItem> fromProduction(final Production production) {
        final Set<LRItem> result = new HashSet<LRItem>();

        for (int i = 0, size = production.rightSide().size(); i <= size; i++) {
            result.add(new LRItem(production, i, new HashSet<Symbol>()));
        }
        return result;
    }

    /**
     * Returns the position of the marker.
     *
     * @return -
     * @since 1.0
     */
    public int getDotIndex() {
        return dotIndex;
    }

    /**
     * Sets the position of the marker.
     *
     * @param dotIndex
     *            new poisiton.
     * @since 1.0
     */
    public void setDotIndex(final int dotIndex) {
        this.dotIndex = dotIndex;
    }

    /**
     * Returns the production of the item.
     *
     * @return -
     * @since 1.0
     */
    public Production getProduction() {
        return production;
    }

    /**
     * Returns the follows set of the item.
     *
     * @return -
     * @since 1.0
     */
    public Set<Symbol> getTerminalSymbols() {
        return terminalSymbols;
    }

    /**
     * Adds terminal symbols to the follows set.
     *
     * @param startsWith
     *            symbols to be added.
     * @since 1.0
     */
    public void addTerminalSymbols(final Set<Symbol> startsWith) {
        terminalSymbols.addAll(startsWith);
    }

    /**
     * Adds a terminal symbol to the follows set.
     *
     * @param symbol
     *            symbol to be added.
     * @since 1.0
     */
    public void addTerminalSymbol(final TerminalSymbol symbol) {
        terminalSymbols.add(symbol);
    }

    @Override
    public int hashCode() {
        return production.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LRItem other = (LRItem) obj;
        if (dotIndex != other.dotIndex) {
            return false;
        }
        if (production == null) {
            if (other.production != null) {
                return false;
            }
        }
        else if (!production.equals(other.production)) {
            return false;
        }
        if (terminalSymbols == null) {
            if (other.terminalSymbols != null) {
                return false;
            }
        }
        else if (!terminalSymbols.equals(other.terminalSymbols)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String result = production.leftSide().toString() + ((production.rightSide().isEmpty()) ? "->null" : "->");
        for (int i = 0, size = production.rightSide().size(); i < size; i++) {
            final Symbol symbol = production.rightSide().get(i);
            if ((dotIndex == size) && (i == (size - 1))) {
                result += symbol.toString() + "*";
            }
            else if (i == dotIndex) {
                result += "*" + symbol.toString();
            }
            else {
                result += symbol.toString();
            }
        }
        result += "{";
        for (final Symbol symbol : terminalSymbols) {
            result += symbol.name();
        }
        return result + "}";
    }

    @Override
    public int compareTo(final LRItem o) {
        return production.compareTo(o.production);
    }
}
