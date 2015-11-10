package hr.fer.zemris.ppj.finite.automaton.generator.builders;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.BasicInput;
import hr.fer.zemris.ppj.finite.automaton.generator.interfaces.TransferFunctionBuilder;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.transfer.ENFAutomatonTransferFunction;
import hr.fer.zemris.ppj.finite.automaton.transfer.EpsilonTransition;
import hr.fer.zemris.ppj.finite.automaton.transfer.FAutomatonTransition;
import hr.fer.zemris.ppj.finite.automaton.transfer.NormalTransition;

/**
 * <code>ENFATransferFunctionBuilder</code> is a builder for transfer function of nondeterministic automaton with
 * e-moves.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class ENFATransferFunctionBuilder implements TransferFunctionBuilder {

    private static final String EMPTY_SEQUENCE = "null";

    Map<String, Map<String, Set<String>>> rawTransitions;

    /**
     * Class constructor.
     *
     * @since 1.0
     */
    public ENFATransferFunctionBuilder() {
        rawTransitions = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.0
     */
    @Override
    public ENFAutomatonTransferFunction build(final Map<String, State> states) {
        final Set<FAutomatonTransition> automatonTransitions = new HashSet<>();
        for (final Entry<String, Map<String, Set<String>>> transition : rawTransitions.entrySet()) {
            final State oldState = states.get(transition.getKey());
            for (final Entry<String, Set<String>> stateTransition : transition.getValue().entrySet()) {
                final String symbol = stateTransition.getKey();
                for (final String newStateId : stateTransition.getValue()) {
                    final State newState = states.get(newStateId);
                    if (symbol.equals(EMPTY_SEQUENCE)) {
                        automatonTransitions.add(new EpsilonTransition(oldState, newState));
                    }
                    else {
                        automatonTransitions.add(new NormalTransition(oldState, newState, new BasicInput(symbol)));
                    }
                }
            }
        }
        return new ENFAutomatonTransferFunction(automatonTransitions);
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.0
     */
    @Override
    public void addTransition(final String oldState, final Character symbol, final String newState) {
        addTransition(oldState, symbol.toString(), newState);
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.0
     */
    @Override
    public void addTransition(final String oldState, final String symbol, final String newState) {
        final Map<String, Set<String>> rawStateTransitions = (rawTransitions.containsKey(oldState)
                ? rawTransitions.get(oldState) : new HashMap<String, Set<String>>());
        final Set<String> rawTransitionResult =
                (rawStateTransitions.containsKey(symbol) ? rawStateTransitions.get(symbol) : new HashSet<String>());

        rawTransitionResult.add(newState);

        rawStateTransitions.put(symbol, rawTransitionResult);
        rawTransitions.put(oldState, rawStateTransitions);
    }
}
