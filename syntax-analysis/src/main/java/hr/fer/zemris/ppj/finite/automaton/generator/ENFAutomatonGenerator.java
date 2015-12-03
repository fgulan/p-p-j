package hr.fer.zemris.ppj.finite.automaton.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.BasicInput;
import hr.fer.zemris.ppj.finite.automaton.BasicState;
import hr.fer.zemris.ppj.finite.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.generator.builders.AlphabetBuilder;
import hr.fer.zemris.ppj.finite.automaton.generator.builders.ENFATransferFunctionBuilder;
import hr.fer.zemris.ppj.finite.automaton.generator.interfaces.AutomatonGenerator;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.transfer.ENFAutomatonTransferFunction;
import hr.fer.zemris.ppj.finite.automaton.transfer.EpsilonTransition;
import hr.fer.zemris.ppj.finite.automaton.transfer.FAutomatonTransition;
import hr.fer.zemris.ppj.finite.automaton.transfer.NormalTransition;
import hr.fer.zemris.ppj.grammar.Grammar;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.grammar.interfaces.Symbol;
import hr.fer.zemris.ppj.grammar.symbols.TerminalSymbol;
import hr.fer.zemris.ppj.lr1.parser.LRItem;
import hr.fer.zemris.ppj.lr1.parser.LRState;
import hr.fer.zemris.ppj.utility.text.manipulation.RegularExpressionManipulator;

/**
 * <code>ENFAutomatonGenerator</code> generates a nondeterministic automaton with e-moves. <br>
 * Important: create a new generator for each automaton you generate.
 *
 * @author Jan Kelemen
 * @author Filip Gulan
 *
 * @version 1.2
 */
public class ENFAutomatonGenerator implements AutomatonGenerator {

    /**
     * <code>StatePair</code> utility class used when generating automaton, contains the initial and the accepting state
     * of the automaton.
     *
     * @author Jan Kelemen
     *
     * @version 1.0
     */
    private class StatePair {

        public StatePair(final State initial, final State accepting) {
            this.initial = initial;
            this.accepting = accepting;
        }

        public State initial;
        public State accepting;
    }

    private final static TerminalSymbol END_SYMBOL = new TerminalSymbol("#");

    private static final String EMPTY_SEQUENCE = "null";

    private final Map<String, State> states = new HashMap<>();

    private final Map<String, State> acceptStates = new HashMap<>();

    private final ENFATransferFunctionBuilder transferFunctionBuilder = new ENFATransferFunctionBuilder();

    private final AlphabetBuilder alphabetBuilder = new AlphabetBuilder();

    private State initial;

    /**
     * {@inheritDoc}
     *
     * @since 1.0
     */
    @Override
    public ENFAutomaton fromRegularExpression(final String expression) {
        final StatePair pair = fromRegularExpressionImpl(expression);

        initial = pair.initial;

        acceptStates.clear();
        acceptStates.put(pair.accepting.getId(), pair.accepting);

        return build();
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.1
     */
    @Override
    public ENFAutomaton fromTextDefinition(final String states, final String acceptStates, final String alphabet,
            final List<String> transitions, final String startState) {
        for (final String stateId : states.split(" ")) {
            this.states.put(stateId, new BasicState(stateId));
        }

        for (final String stateId : acceptStates.split(" ")) {
            this.acceptStates.put(stateId, new BasicState(stateId));
        }

        initial = this.states.get(startState);

        for (final String symbol : alphabet.split(" ")) {
            alphabetBuilder.addSymbol(escapeString(symbol));
        }

        for (final String transition : transitions) {
            final String[] split = transition.split(" ");
            transferFunctionBuilder.addTransition(split[0],
                    split[1].equals("null") ? EMPTY_SEQUENCE : escapeString(split[1]), split[2]);
        }

        return build();
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.2
     */
    @Override
    public ENFAutomaton fromLR1Grammar(final Grammar grammar) {
        final Set<Input> alphabet = new HashSet<>();
        final Set<FAutomatonTransition> transitions = new HashSet<>();
        final Set<State> automatonStates = new HashSet<>();

        final List<LRItem> items = new ArrayList<LRItem>();
        final Map<LRItem, LRState> states = new HashMap<>();

        for (final Production production : grammar.productions()) {
            for (final LRItem item : LRItem.fromProduction(production)) {
                items.add(item);
            }
        }
        final LRState startState = createStartState(grammar);
        states.put(startState.getItems().get(0), startState);
        automatonStates.add(startState);

        // Generate alphabet symbols
        for (final Symbol symbol : grammar.terminalSymbols()) {
            alphabet.add(new BasicInput(symbol));
        }
        for (final Symbol symbol : grammar.nonterminalSymbols()) {
            alphabet.add(new BasicInput(symbol));
        }

        final Set<LRItem> finishedItems = new HashSet<>();
        int stateIndex = 1;
        boolean changed = true;
        while (changed) {
            changed = false;
            final Map<LRItem, LRState> addedStates = new HashMap<>();

            for (final Entry<LRItem, LRState> entry : states.entrySet()) {
                final LRState state = entry.getValue();
                final LRItem item = entry.getKey();
                if (finishedItems.contains(item)) {
                    continue;
                }

                Symbol currentSymbol = null;
                final int dotIndex = item.getDotIndex();
                if (dotIndex < item.getProduction().rightSide().size()) {
                    currentSymbol = item.getProduction().rightSide().get(dotIndex);
                }

                final LRItem nextItem = getItemWithNextDot(item, items);
                if (nextItem != null) {
                    final Symbol symbol = item.getProduction().rightSide().get(dotIndex);
                    final LRItem newItem =
                            new LRItem(nextItem.getProduction(), nextItem.getDotIndex(), item.getTerminalSymbols());
                    LRState nextState = states.get(newItem);
                    final LRState addedState = addedStates.get(newItem);

                    if ((nextState == null) && (addedState == null)) {
                        nextState = new LRState(new ArrayList<LRItem>(Arrays.asList(newItem)), stateIndex++);
                        automatonStates.add(nextState);
                        addedStates.put(newItem, nextState);
                        changed = true;
                    }
                    else if (nextState == null) {
                        nextState = addedState;
                    }
                    final NormalTransition transition = new NormalTransition(state, nextState, new BasicInput(symbol));
                    transitions.add(transition);
                }

                if ((currentSymbol != null) && !currentSymbol.isTerminal()) {
                    final int size = item.getProduction().rightSide().size();
                    final List<Symbol> leftSymbols = new ArrayList<>();
                    for (int i = dotIndex + 1; i < size; i++) {
                        leftSymbols.add(item.getProduction().rightSide().get(i));
                    }
                    final boolean emptySequence = grammar.isEmptySequence(leftSymbols);
                    final Set<Symbol> startsWith = grammar.startsWith(leftSymbols);
                    if (emptySequence) {
                        startsWith.addAll(item.getTerminalSymbols());
                    }

                    final List<LRItem> newItems = getStartItems(currentSymbol, items);
                    for (final LRItem currItem : newItems) {
                        final LRItem newItem = new LRItem(currItem.getProduction(), currItem.getDotIndex(), startsWith);

                        LRState nextState = states.get(newItem);
                        final LRState addedState = addedStates.get(newItem);
                        if ((nextState == null) && (addedState == null)) {
                            nextState = new LRState(new ArrayList<LRItem>(Arrays.asList(newItem)), stateIndex++);
                            addedStates.put(newItem, nextState);
                            automatonStates.add(nextState);
                            changed = true;
                        }
                        else if (nextState == null) {
                            nextState = addedState;
                        }
                        final EpsilonTransition transition = new EpsilonTransition(state, nextState);
                        transitions.add(transition);
                    }
                    if (changed) {
                        break;
                    }
                }
                finishedItems.add(item);
            }
            states.putAll(addedStates);
        }
        final ENFAutomatonTransferFunction transferFunction = new ENFAutomatonTransferFunction(transitions);
        return new ENFAutomaton(automatonStates, automatonStates, alphabet, transferFunction, startState);
    }

    /*
     * Creates a starting state of the automaton.
     */
    private LRState createStartState(final Grammar grammar) {
        final Production production = grammar.getStartProduction();
        final HashSet<Symbol> terminalSymbols = new HashSet<>();
        terminalSymbols.add(END_SYMBOL);
        final LRItem startItem = new LRItem(production, 0, terminalSymbols);
        return new LRState(Arrays.asList(new LRItem[] { startItem }), 0);
    }

    /*
     * Finds the follows set.
     */
    private List<LRItem> getStartItems(final Symbol symbol, final List<LRItem> items) {
        final List<LRItem> newItems = new ArrayList<>();
        for (final LRItem item : items) {
            if (item.getProduction().leftSide().equals(symbol) && (item.getDotIndex() == 0)) {
                newItems.add(item);
            }
        }
        return newItems;
    }

    /*
     * Returns the next LR item.
     */
    private LRItem getItemWithNextDot(final LRItem item, final List<LRItem> items) {
        for (final LRItem tempItem : items) {
            if ((tempItem.getDotIndex() == (item.getDotIndex() + 1))
                    && tempItem.getProduction().equals(item.getProduction())) {
                return tempItem;
            }
        }
        return null;
    }

    /*
     * Implements Thompson's construction algorithm:
     */
    private StatePair fromRegularExpressionImpl(final String expression) {
        final List<String> subexpressions = RegularExpressionManipulator.splitOnOperator(expression, '|');
        final StatePair pair = new StatePair(newBasicState(false), newBasicState(true));

        if (subexpressions.size() > 1) {
            for (final String subexpression : subexpressions) {
                final StatePair subpair = fromRegularExpressionImpl(subexpression);
                addTransition(pair.initial, subpair.initial);
                addTransition(subpair.accepting, pair.accepting);
            }
        }
        else {
            boolean prefixed = false;
            State lastState = pair.initial;

            for (int i = 0; i < expression.length(); i++) {
                StatePair subpair = new StatePair(null, null);

                if (prefixed) { // Slucaj 1
                    prefixed = false;
                    subpair = new StatePair(newBasicState(false), newBasicState(false));
                    addTransition(subpair.initial, subpair.accepting,
                            String.valueOf(unprefixedSymbol(expression.charAt(i))));
                }
                else { // Slucaj 2
                    if (expression.charAt(i) == '\\') {
                        prefixed = true;
                        continue;
                    }

                    if (expression.charAt(i) == '(') {
                        final int j = RegularExpressionManipulator.findClosingBracket(expression, i, '(', ')');

                        subpair = fromRegularExpressionImpl(expression.substring(i + 1, j));

                        i = j;
                    }
                    else {
                        subpair = new StatePair(newBasicState(false), newBasicState(false));
                        addTransition(subpair.initial, subpair.accepting,
                                expression.charAt(i) == '$' ? EMPTY_SEQUENCE : String.valueOf(expression.charAt(i)));
                    }
                }

                // Kleene operator check
                if (((i + 1) < expression.length()) && (expression.charAt(i + 1) == '*')) {
                    final StatePair oldPair = subpair;
                    subpair = new StatePair(newBasicState(false), newBasicState(false));

                    addTransition(subpair.initial, oldPair.initial);
                    addTransition(subpair.initial, subpair.accepting);
                    addTransition(oldPair.accepting, subpair.accepting);
                    addTransition(oldPair.accepting, oldPair.initial);

                    i++;
                }

                // Merge with last subexpression
                addTransition(lastState, subpair.initial);
                lastState = subpair.accepting;
            }
            addTransition(lastState, pair.accepting);
        }
        return pair;
    }

    /*
     * Builds the automaton. EVERYTHING must be correctly defined.
     */
    private ENFAutomaton build() {
        final Set<State> states = new HashSet<State>(this.states.values());
        final Set<State> acceptStates = new HashSet<State>(this.acceptStates.values());

        final Set<Input> alphabet = alphabetBuilder.build();

        final ENFAutomatonTransferFunction transferFunction = transferFunctionBuilder.build(this.states);

        return new ENFAutomaton(states, acceptStates, alphabet, transferFunction, initial);
    }

    /*
     * Creates a new state of the automaton.
     */
    private State newBasicState(final boolean acceptance) {
        final State state = new BasicState(String.valueOf(states.size()));

        states.put(state.getId(), state);
        if (acceptance) {
            acceptStates.put(state.getId(), state);
        }

        return state;
    }

    /*
     * Adds a transition for the empty sequence (e-move) to the automaton.
     */
    private void addTransition(final State oldState, final State newState) {
        transferFunctionBuilder.addTransition(oldState.getId(), EMPTY_SEQUENCE, newState.getId());
    }

    /*
     * Adds a transition for the specified symbol to the automaton.
     */
    private void addTransition(final State oldState, final State newState, final String symbol) {
        transferFunctionBuilder.addTransition(oldState.getId(), symbol, newState.getId());

        alphabetBuilder.addSymbol(symbol);
    }

    /*
     * Used when a prefixed symbol is foumd in the regular expression to get the real representation of the symbol.
     */
    private char unprefixedSymbol(final char symbol) {
        switch (symbol) {
            case 't':
                return '\t';
            case 'n':
                return '\n';
            case '_':
                return ' ';
            default:
                return symbol;
        }
    }

    /*
     * Used when reading from a text definition to get unescaped symbols for transitions and alphabet.
     */
    private static String escapeString(final String input) {
        switch (input) {
            case "\\n":
                return "\n";
            case "\\r":
                return "\r";
            case "\\t":
                return "\t";
            case "\\_":
                return " ";
            default:
                return input;
        }
    }

}
