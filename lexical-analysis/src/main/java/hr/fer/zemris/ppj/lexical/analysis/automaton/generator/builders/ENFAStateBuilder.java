package hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders;

import hr.fer.zemris.ppj.lexical.analysis.automaton.BasicState;
import hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.interfaces.StateBuilder;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;

/**
 * <code>ENFAStateBuilder</code> is a builder for states of a nondeterministic automaton with e-moves.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class ENFAStateBuilder extends AbstractStateBuilder implements StateBuilder {

    /**
     * Class constructor specifies the state id and the acceptance.
     *
     * @param number
     *            the state id.
     * @param acceptance
     *            the acceptance of the state.
     * @since 1.0
     */
    public ENFAStateBuilder(final String number, final boolean acceptance) {
        super(number, acceptance);
    }

    /**
     * {@inheritDoc}
     *
     * @see hr.fer.zemris.ppj.lexical.analysis.automaton.generator.builders.interfaces.StateBuilder#build()
     * @since 1.0
     */
    @Override
    public State build() {
        return new BasicState(getId());
    }

}
