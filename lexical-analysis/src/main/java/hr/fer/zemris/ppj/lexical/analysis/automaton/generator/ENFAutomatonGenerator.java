package hr.fer.zemris.ppj.lexical.analysis.automaton.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.AlphabetBuilder;
import hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.ENFAStateBuilder;
import hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.ENFATransferFunctionBuilder;
import hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.interfaces.StateBuilder;
import hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.interfaces.TransferFunctionBuilder;
import hr.fer.zemris.ppj.lexical.analysis.automaton.generator.interfaces.AutomatonGenerator;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Automaton;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.TransferFunction;
import hr.fer.zemris.ppj.lexical.analysis.text.manipulation.RegularExpressionManipulator;

/**
 * <code>ENFAutomatonGenerator</code> generates a nondeterministic automaton with e-moves. <br>
 * Important: create a new generator for each automaton you generate.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
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

    private final TransferFunctionBuilder transferFunctionBuilder;

    private final AlphabetBuilder alphabetBuilder;

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
     * @see hr.fer.zemris.ppj.lexical.analysis.automaton.generator.interfaces.AutomatonGenerator#fromRegularExpression(java.lang.String)
     * @since 1.0
     */
    @Override
    public Automaton fromRegularExpression(final String expression) {
        final StateBuilderPair pair = fromRegularExpressionImpl(expression);

        // Build alphabet
        final Set<Input> alphabet = alphabetBuilder.build();

        // Build states
        final List<State> states = new ArrayList<>();
        final List<State> acceptingStates = new ArrayList<>();
        State startState = null;
        final boolean foundInitial = false;
        for (final StateBuilder builder : stateBuilders.values()) {
            final State state = builder.build();

            if (!foundInitial && pair.initial.getId().equals(builder.getId())) {
                startState = state;
            }

            if (builder.isAccepting()) {
                acceptingStates.add(state);
            }
        }

        // Build transfer function
        final TransferFunction transferFunction = transferFunctionBuilder.build(states);

        // TODO: Build the automaton
        return null;
    }

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

            return pair;
        }

        boolean prefixed = false;
        final StateBuilder lastState = pair.initial;

        for (int i = 0; i < expression.length(); i++) {
            StateBuilderPair subpair = new StateBuilderPair(null, null);

            // Early escapes
            if (prefixed) {
                subpair = new StateBuilderPair(newStateBuilder(false), newStateBuilder(false));
                addTransition(subpair.initial, subpair.accepting, unprefixedSymbol(expression.charAt(i)));
                addTransition(lastState, pair.accepting);

                continue;
            }
            else if (expression.charAt(i) == '\\') {
                prefixed = false;

                continue;
            }

            // Concatenation
            if (expression.charAt(i) == '(') { // slucaj 2a <-> slucaj 2b
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

            // Kleene operator check
            if (((i + 1) < expression.length()) && (expression.charAt(i) == '*')) {
                final StateBuilderPair oldPair = subpair;
                subpair = new StateBuilderPair(newStateBuilder(false), newStateBuilder(false));

                addTransition(subpair.initial, oldPair.initial);
                addTransition(subpair.initial, subpair.accepting);
                addTransition(oldPair.accepting, subpair.accepting);
                addTransition(oldPair.accepting, oldPair.initial);

                i++;
            }

            addTransition(lastState, subpair.initial);
            subpair.accepting = lastState;

            addTransition(lastState, pair.accepting);
        }
        return pair;
    }

    private ENFAStateBuilder newStateBuilder(final boolean acceptance) {
        final ENFAStateBuilder builder = new ENFAStateBuilder(String.valueOf(stateBuilders.size()), acceptance);
        stateBuilders.put(builder.getId(), builder);
        return builder;
    }

    private void addTransition(final StateBuilder oldState, final StateBuilder newState) {
        transferFunctionBuilder.addTransition(oldState.getId(), EMPTY_SEQUENCE, newState.getId());
    }

    private void addTransition(final StateBuilder oldState, final StateBuilder newState, final Character symbol) {
        transferFunctionBuilder.addTransition(oldState.getId(), symbol, newState.getId());

        alphabetBuilder.addSymbol(symbol);
    }

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

}
