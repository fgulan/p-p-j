package hr.fer.zemris.ppj.grammar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.ppj.grammar.interfaces.Symbol;
import hr.fer.zemris.ppj.grammar.symbols.NonterminalSymbol;
import hr.fer.zemris.ppj.grammar.symbols.TerminalSymbol;

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
    public GrammarBuilder(final Set<String> nonterminalSymbols, final Set<String> terminalSymbols,
            final String startSymbol) {

        for (String name : nonterminalSymbols) {
            Symbol symbol = new NonterminalSymbol(name);

            if (name.equals(startSymbol)) {
                this.startSymbol = symbol;
            }

            this.nonterminalSymbols.add(new NonterminalSymbol(name));
        }

        for (String name : terminalSymbols) {
            this.terminalSymbols.add(new TerminalSymbol(name));
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
     * @param leftSide
     *            left side of the production.
     * @param rightSide
     *            right side of the production.
     * @since alpha
     */
    public void addProduction(final String leftSide, final String rightSide) {
        List<Symbol> right = new ArrayList<>();

        for (String name : rightSide.split(" ")) {
            if (name.equals("$")) {
                break;
            }

            if (name.startsWith("<")) {
                right.add(new NonterminalSymbol(name));
            }
            else {
                right.add(new TerminalSymbol(name));
            }
        }

        productions.add(new Production(new NonterminalSymbol(leftSide), right));
    }
}
