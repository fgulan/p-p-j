package hr.fer.zemris.ppj.grammar;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.ppj.grammar.interfaces.Symbol;

/**
 * <code>GrammarBuilder</code> is a builder for context free grammars.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class GrammarBuilder {

    private final Set<Symbol> nonterminalSymbols = new HashSet<>();
    private final Set<Symbol> terminalSymbols = new HashSet<>();
    private final Set<Production> productions = new HashSet<>();
    private Symbol startSymbol;

    /**
     * Class constructor, specifies the nonterminal and terminal symbols and start symbol of the grammar.
     *
     * @param nonterminalSymbols
     *            the nonterminal symbols.
     * @param terminalSymbols
     *            the terminal symbols.
     * @param startSymbol
     *            the start
     * @since alpha
     */
    public GrammarBuilder(final List<String> nonterminalSymbols, final List<String> terminalSymbols,
            final String startSymbol) {

        for (String name : nonterminalSymbols) {
            Symbol symbol = ProductionParser.parseSymbol(name);

            if (name.equals(startSymbol)) {
                this.startSymbol = symbol;
            }

            this.nonterminalSymbols.add(ProductionParser.parseSymbol(name));
        }

        for (String name : terminalSymbols) {
            this.terminalSymbols.add(ProductionParser.parseSymbol(name));
        }

    }

    /**
     * Constructs the grammar.
     *
     * @return the grammar.
     * @since alpha
     */
    public Grammar build() {
        return new Grammar(nonterminalSymbols, terminalSymbols, productions, startSymbol);
    }

    /**
     * Adds a production to the grammar.
     *
     * @param production
     *            the production thats being added.
     *
     * @since alpha
     */
    public void addProduction(Production production) {
        productions.add(production);
    }
}
