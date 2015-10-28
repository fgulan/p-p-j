package hr.fer.zemris.ppj.finite.automaton.transfer;

import java.util.HashSet;
import java.util.Set;

public class DFAutomatonTransferFunction extends FAutomatonTransferFunction {

    public DFAutomatonTransferFunction(final Set<DeterministicTransition> transitions) {
        super(new HashSet<FAutomatonTransition>(transitions));
    }

}
