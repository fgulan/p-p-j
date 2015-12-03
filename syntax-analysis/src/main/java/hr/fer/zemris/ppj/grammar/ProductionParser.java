package hr.fer.zemris.ppj.grammar;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.grammar.interfaces.Symbol;
import hr.fer.zemris.ppj.grammar.symbols.NonterminalSymbol;
import hr.fer.zemris.ppj.grammar.symbols.TerminalSymbol;

/**
 * <code>ProductionParser</code> is a utility class which parses context free grammar productions.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class ProductionParser {

    private static int productionCounter = 0;

    /**
     * Parses a production from the string definition.
     *
     * @param production
     *            the production.
     * @return parsed production.
     * @since 1.0
     */
    public static Production fromText(final String production) {
        final String[] symbols = production.split(" ");

        final Symbol leftSide = parseSymbol(symbols[0]);
        final List<Symbol> rightSide = new ArrayList<>();
        for (int i = 1; i < symbols.length; i++) {
            final Symbol symbol = parseSymbol(symbols[i]);
            if (symbol != null) {
                rightSide.add(symbol);
            }
        }

        return new Production(leftSide, rightSide, productionCounter++);
    }

    /**
     * Parses a production from the string definition.
     *
     * @param leftSide
     *            the left side of the production.
     * @param rightSide
     *            the right side of the production.
     * @return parsed production.
     * @since 1.0
     */
    public static Production fromText(final String leftSide, final String rightSide) {
        return fromText(leftSide + " " + rightSide);
    }

    /**
     * Parses a symbol from the string.
     *
     * @param symbol
     *            the string representation of the symbol.
     * @return the parsed symbol.
     * @since 1.0
     */
    public static Symbol parseSymbol(final String symbol) {
        if (symbol.equals("$") || symbol.equals("null")) {
            return null;
        }
        if (symbol.startsWith("<")) {
            return new NonterminalSymbol(symbol);
        }
        else {
            return new TerminalSymbol(symbol);
        }
    }
}
