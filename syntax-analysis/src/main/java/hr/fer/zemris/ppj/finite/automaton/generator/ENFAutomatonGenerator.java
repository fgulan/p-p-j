package hr.fer.zemris.ppj.finite.automaton.generator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.BasicState;
import hr.fer.zemris.ppj.finite.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.generator.builders.AlphabetBuilder;
import hr.fer.zemris.ppj.finite.automaton.generator.builders.ENFATransferFunctionBuilder;
import hr.fer.zemris.ppj.finite.automaton.generator.interfaces.AutomatonGenerator;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Automaton;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.transfer.ENFAutomatonTransferFunction;
import hr.fer.zemris.ppj.utility.text.manipulation.RegularExpressionManipulator;

/**
 * <code>ENFAutomatonGenerator</code> generates a nondeterministic automaton with e-moves. <br>
 * Important: create a new generator for each automaton you generate.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
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

    private static final char EMPTY_SEQUENCE = '\0';

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
    public Automaton fromTextDefinition(final String states, final String acceptStates, final String alphabet,
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
                    addTransition(subpair.initial, subpair.accepting, unprefixedSymbol(expression.charAt(i)));
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
                                expression.charAt(i) == '$' ? EMPTY_SEQUENCE : expression.charAt(i));
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

        return new ENFAutomaton(states, acceptStates, alphabet, transferFunction, this.states.get(initial));
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
    private void addTransition(final State oldState, final State newState, final Character symbol) {
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
    private static Character escapeString(final String input) {
        Character output = input.charAt(0);
        switch (input) {
            case "\\n":
                output = '\n';
                break;
            case "\\r":
                output = '\r';
                break;
            case "\\t":
                output = '\t';
                break;
            case "\\_":
                output = ' ';
                break;
            default:
                break;
        }
        return output;
    }

}
