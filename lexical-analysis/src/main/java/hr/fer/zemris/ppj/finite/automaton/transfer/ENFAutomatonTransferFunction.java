package hr.fer.zemris.ppj.finite.automaton.transfer;

import java.util.Set;

/**
 * <code>ENFAutomatonTransferFunction</code> is a transfer function for nondeterministic finite automaton with e-moves.
 *
 * @author Jan Kelemen
 *
 * @see hr.fer.zemris.ppj.finite.automaton.interfaces.TransferFunction
 * @version 1.0
 */
public class ENFAutomatonTransferFunction extends FAutomatonTransferFunction {

    /**
     * Class constructor, specifies the transitions of the transfer function.
     *
     * @param transitions
     *            the transitions.
     * @since 1.0
     */
    public ENFAutomatonTransferFunction(final Set<FAutomatonTransition> transitions) {
        super(transitions);
    }

}
