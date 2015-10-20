package hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.interfaces.TransferFunctionBuilder;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.TransferFunction;

/**
 * <code>ENFATransferFunctionBuilder</code> is a builder for transfer function of nondeterministic automaton with
 * e-moves.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class ENFATransferFunctionBuilder implements TransferFunctionBuilder {

    Map<String, Map<Character, Set<String>>> rawTransitions;

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
     * @see hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.interfaces.TransferFunctionBuilder#build(java.util.List)
     * @since
     */
    @Override
    public TransferFunction build(final List<State> states) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.interfaces.TransferFunctionBuilder#addTransition(String,
     *      Character, String)
     * @since 1.0
     */
    @Override
    public void addTransition(final String oldState, final Character symbol, final String newState) {
        final Map<Character, Set<String>> rawStateTransitions =
                rawTransitions.containsKey(oldState) ? rawTransitions.get(symbol) : new HashMap<>();
        final Set<String> rawTransitionResult =
                rawStateTransitions.containsKey(symbol) ? rawStateTransitions.get(symbol) : new HashSet<>();

        rawTransitionResult.add(newState);

        rawStateTransitions.put(symbol, rawTransitionResult);
        rawTransitions.put(oldState, rawStateTransitions);
    }

}
