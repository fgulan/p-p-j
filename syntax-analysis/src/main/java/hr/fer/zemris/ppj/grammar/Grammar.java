package hr.fer.zemris.ppj.grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.ppj.grammar.interfaces.Symbol;

/**
 * <code>Grammar</code> represents a context free grammar
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class Grammar {

    private final Map<String, Symbol> nonterminalSymbols = new HashMap<>();
    private final Map<String, Symbol> terminalSymbols = new HashMap<>();
    private final Map<Symbol, List<Production>> productions = new HashMap<>();
    private Symbol startSymbol;

    private Set<Symbol> emptySymbols = new HashSet<>();
    private Map<Symbol, Set<Symbol>> startsWith = new HashMap<>();

    private Set<Production> productionSet = new HashSet<>();

    /**
     * Class constructor, specifies the formal definition of the grammar.
     *
     * @param nonterminalSymbols
     *            the nonterminal symbols.
     * @param terminalSymbols
     *            the terminal symbols.
     * @param productions
     *            the productions.
     * @param startSymbol
     *            the start symbol.
     * @since alpha
     */
    public Grammar(Set<Symbol> nonterminalSymbols, Set<Symbol> terminalSymbols, Set<Production> productions,
            Symbol startSymbol) {

        for (Symbol symbol : nonterminalSymbols) {
            this.nonterminalSymbols.put(symbol.toString(), symbol);
        }

        for (Symbol symbol : terminalSymbols) {
            this.terminalSymbols.put(symbol.toString(), symbol);
        }

        for (Production production : productions) {
            Symbol leftSide = production.leftSide();

            List<Production> productionList = this.productions.containsKey(leftSide) ? this.productions.get(leftSide)
                    : new ArrayList<Production>();

            productionList.add(production);

            productionSet.add(production);

            this.productions.put(leftSide, productionList);
        }

        this.startSymbol = startSymbol;

        calculateEmptySymbols();
        calculateStartsWith();
    }

    private void calculateEmptySymbols() {
        boolean change = false;
        do {
            change = false;
            for (Symbol symbol : nonterminalSymbols.values()) {
                if (!emptySymbols.contains(symbol)) {
                    for (Production production : productions.get(symbol)) {
                        boolean allEmpty = true;
                        if (!production.isEpsilonProduction()) {
                            for (Symbol productionSymbol : production.rightSide()) {
                                if (!productionSymbol.isTerminal() || !emptySymbols.contains(productionSymbol)) {
                                    allEmpty = false;
                                    break;
                                }
                            }
                        }
                        if (allEmpty) {
                            change = true;
                            emptySymbols.add(symbol);
                            break;
                        }
                    }
                }
            }
        } while (change);

        emptySymbols.add(ProductionParser.parseSymbol("$"));
    }

    private void calculateStartsWith() {
        List<Symbol> orderedSymbols = new ArrayList<>();
        orderedSymbols.addAll(nonterminalSymbols.values());
        orderedSymbols.addAll(terminalSymbols.values());
        Collections.sort(orderedSymbols);

        boolean[][] table = new boolean[orderedSymbols.size()][];
        for (int i = 0; i < table.length; i++) {
            table[i] = new boolean[orderedSymbols.size()];
        }

        // ZapocinjeIzravnoZnakom
        for (int i = 0; i < table.length; i++) {
            Symbol symbol = orderedSymbols.get(i);
            if (productions.containsKey(symbol)) {
                for (Production production : productions.get(symbol)) {
                    if (!production.isEpsilonProduction()) {
                        for (Symbol productionSymbol : production.rightSide()) {
                            table[i][orderedSymbols.indexOf(productionSymbol)] = true;
                            if (!emptySymbols.contains(productionSymbol)) {
                                break;
                            }
                        }
                    }

                }
            }
        }

        // ZapocinjeZnakom
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j]) {
                    for (int k = 0; k < table[j].length; k++) {
                        if (table[j][k]) {
                            table[i][k] = true;
                        }
                    }
                }
                if (i == j) {
                    table[i][j] = true;
                }
            }
        }

        // Read from table
        for (int i = 0; i < table.length; i++) {
            Set<Symbol> starts = new HashSet<>();
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] && orderedSymbols.get(j).isTerminal()) {
                    starts.add(orderedSymbols.get(j));
                }
            }
            startsWith.put(orderedSymbols.get(i), starts);
        }

    }

    /**
     * Checks if a symbol can generate a empty sequence.
     *
     * @param symbol
     *            the symbol.
     * @return <code>true</code> if a symbol can generatea empty
     * @since alpha
     */
    public boolean isEmptySymbol(Symbol symbol) {
        if (symbol == null) {
            return true;
        }

        return emptySymbols.contains(symbol);
    }

    /**
     * Checks if a given sequence of symbols can generate a empty sequence.
     *
     * @param sequence
     *            the sequence.
     * @return <code>true</code> if the empty sequence can be generated, <code>false</code> otherwise.
     * @since alpha
     */
    public boolean isEmptySequence(List<Symbol> sequence) {
        for (Symbol symbol : sequence) {
            if (!emptySymbols.contains(symbol)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a given sequence of symbols can generate a empty sequence.
     *
     * @param sequence
     *            the sequence.
     * @return <code>true</code> if the empty sequence can be generated, <code>false</code> otherwise.
     * @since alpha
     */
    public boolean isEmptySequence(String sequence) {
        List<Symbol> newSequence = new ArrayList<>();

        if (sequence.equals("$")) {
            return true;
        }

        for (String name : sequence.split(" ")) {
            newSequence.add(ProductionParser.parseSymbol(name));
        }

        return isEmptySequence(newSequence);
    }

    /**
     * Calculates the set of terminal symbols that can appear instead of the specified symbol.
     *
     * @param symbol
     *            the symbol.
     * @return symbols that are possible instead of the specified symbol.
     * @since alpha
     */
    public Set<Symbol> startsWith(Symbol symbol) {
        Set<Symbol> result = new HashSet<>();

        if (symbol == null) {
            return result;
        }

        result.addAll(startsWith.get(symbol));

        return result;
    }

    /**
     * Calculates the set of symbols that can appear instead of the specified sequence of symbols.
     *
     * @param sequence
     *            the sequence.
     * @return symbols that are possible instead of the specified sequence.
     * @since alpha
     */
    public Set<Symbol> startsWith(List<Symbol> sequence) {
        Set<Symbol> result = new HashSet<>();

        for (Symbol symbol : sequence) {
            if (symbol.isTerminal()) {
                result.add(symbol);
                break;
            }

            if (!isEmptySymbol(symbol)) {
                result.addAll(startsWith.get(symbol));
                break;
            }

            result.addAll(startsWith.get(symbol));
        }

        return result;
    }

    /**
     * Calculates the set of symbols that can appear instead of the specified sequence of symbols.
     *
     * @param sequence
     *            the sequence.
     * @return symbols that are possible instead of the specified sequence.
     * @since alpha
     */
    public Set<Symbol> startsWith(String sequence) {
        List<Symbol> newSequence = new ArrayList<>();

        if (!sequence.equals("$")) {
            for (String name : sequence.split(" ")) {
                newSequence.add(ProductionParser.parseSymbol(name));
            }
        }

        return startsWith(newSequence);
    }

    /**
     * Returns all productions of the grammar.
     *
     * @return -
     * @since alpha
     */
    public Set<Production> productions() {
        return new HashSet<>(productionSet);
    }

    /**
     * Returns terminal symbols of the grammar.
     *
     * @return -
     * @since alpha
     */
    public Set<Symbol> terminalSymbols() {
        Set<Symbol> result = new HashSet<>();
        result.addAll(terminalSymbols.values());
        return result;
    }

    /**
     * Returns nonterminal symbols of the grammar.
     *
     * @return -
     * @since alpha
     */
    public Set<Symbol> nonterminalSymbols() {
        Set<Symbol> result = new HashSet<>();
        result.addAll(nonterminalSymbols.values());
        return result;
    }

    /**
     * Returns start symbol of the grammar.
     *
     * @return -
     * @since alpha
     */
    public Symbol startSymbol() {
        return startSymbol;
    }

    public Production getStartProduction() {
        return productions.get(startSymbol).get(0);
    }
}
