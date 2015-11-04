package hr.fer.zemris.ppj.finite.automaton.transfer;

import java.util.HashSet;
import java.util.Set;

/**
 * <code>DFAutomatonTransferFunction</code> is a transfer function for deterministic finite automaton.
 *
 * @author Jan Kelemen
 *
 * @see hr.fer.zemris.ppj.finite.automaton.interfaces.TransferFunction
 * @version 1.0
 */
public class DFAutomatonTransferFunction extends FAutomatonTransferFunction {

    /**
     * Class constructor, specifies the transitions of the transfer function.
     *
     * @param transitions
     *            the transitions.
     * @since 1.0
     */
    public DFAutomatonTransferFunction(final Set<DeterministicTransition> transitions) {
        super(new HashSet<FAutomatonTransition>(transitions));
    }

}
