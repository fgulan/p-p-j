package hr.fer.zemris.ppj.finite.automaton.generator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.generator.builders.AlphabetBuilder;
import hr.fer.zemris.ppj.finite.automaton.generator.builders.BasicStateBuilder;
import hr.fer.zemris.ppj.finite.automaton.generator.builders.ENFATransferFunctionBuilder;
import hr.fer.zemris.ppj.finite.automaton.generator.interfaces.AutomatonGenerator;
import hr.fer.zemris.ppj.finite.automaton.generator.interfaces.StateBuilder;
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
     * <code>StateBuilderPair</code> utility class used when generating automaton, contains the initial and the
     * accepting state of the automaton.
     *
     * @author Jan Kelemen
     *
     * @version 1.0
     */
    private class StateBuilderPair {

        public StateBuilderPair(final StateBuilder initial, final StateBuilder accepting) {
            this.initial = initial;
            this.accepting = accepting;
        }

        public StateBuilder initial;
        public StateBuilder accepting;
    }

    private static final char EMPTY_SEQUENCE = '\0';

    private final Map<String, StateBuilder> stateBuilders;

    private final ENFATransferFunctionBuilder transferFunctionBuilder;

    private final AlphabetBuilder alphabetBuilder;

    private String initial;

    /**
     * Class constructor, does nothing. use some method to generate the automaton.
     *
     * @since 1.0
     */
    public ENFAutomatonGenerator() {
        stateBuilders = new HashMap<>();
        transferFunctionBuilder = new ENFATransferFunctionBuilder();
        alphabetBuilder = new AlphabetBuilder();
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.0
     */
    @Override
    public ENFAutomaton fromRegularExpression(final String expression) {
        final StateBuilderPair pair = fromRegularExpressionImpl(expression);

        for (final StateBuilder builder : stateBuilders.values()) {
            if (pair.initial.getId().equals(builder.getId())) {
                initial = builder.getId();
                break;
            }
        }

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
            stateBuilders.put(stateId, new BasicStateBuilder(stateId, false));
        }

        for (final String stateId : acceptStates.split(" ")) {
            stateBuilders.get(stateId).changeAcceptance(true);
        }

        initial = startState;

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
    private StateBuilderPair fromRegularExpressionImpl(final String expression) {
        final List<String> subexpressions = RegularExpressionManipulator.splitOnOperator(expression, '|');
        final StateBuilderPair pair = new StateBuilderPair(newStateBuilder(false), newStateBuilder(true));

        if (subexpressions.size() > 1) {
            for (final String subexpression : subexpressions) {
                final StateBuilderPair subpair = fromRegularExpressionImpl(subexpression);
                addTransition(pair.initial, subpair.initial);
                addTransition(subpair.accepting, pair.accepting);
                subpair.accepting.changeAcceptance(false);
            }
        }
        else {
            boolean prefixed = false;
            StateBuilder lastState = pair.initial;

            for (int i = 0; i < expression.length(); i++) {
                StateBuilderPair subpair = new StateBuilderPair(null, null);

                if (prefixed) { // Slucaj 1
                    prefixed = false;
                    subpair = new StateBuilderPair(newStateBuilder(false), newStateBuilder(false));
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
                        subpair.accepting.changeAcceptance(false);

                        i = j;
                    }
                    else {
                        subpair = new StateBuilderPair(newStateBuilder(false), newStateBuilder(false));
                        addTransition(subpair.initial, subpair.accepting,
                                expression.charAt(i) == '$' ? EMPTY_SEQUENCE : expression.charAt(i));
                    }
                }

                // Kleene operator check
                if (((i + 1) < expression.length()) && (expression.charAt(i + 1) == '*')) {
                    final StateBuilderPair oldPair = subpair;
                    subpair = new StateBuilderPair(newStateBuilder(false), newStateBuilder(false));

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
        final Map<String, State> statesMap = new HashMap<>();
        final Set<State> states = new HashSet<State>();
        final Set<State> acceptStates = new HashSet<State>();

        for (final StateBuilder builder : stateBuilders.values()) {
            final State state = builder.build();
            statesMap.put(state.getId(), state);
            states.add(state);
            if (builder.isAccepting()) {
                acceptStates.add(state);
            }
        }

        final Set<Input> alphabet = alphabetBuilder.build();

        final ENFAutomatonTransferFunction transferFunction = transferFunctionBuilder.build(statesMap);

        return new ENFAutomaton(states, acceptStates, alphabet, transferFunction, statesMap.get(initial));
    }

    /*
     * Creates a new state builder used in automaton creation.
     */
    private StateBuilder newStateBuilder(final boolean acceptance) {
        final StateBuilder builder = new BasicStateBuilder(String.valueOf(stateBuilders.size()), acceptance);
        stateBuilders.put(builder.getId(), builder);
        return builder;
    }

    /*
     * Adds a transition for the empty sequence (e-move) to the automaton.
     */
    private void addTransition(final StateBuilder oldState, final StateBuilder newState) {
        transferFunctionBuilder.addTransition(oldState.getId(), EMPTY_SEQUENCE, newState.getId());
    }

    /*
     * Adds a transition for the specified symbol to the automaton.
     */
    private void addTransition(final StateBuilder oldState, final StateBuilder newState, final Character symbol) {
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
