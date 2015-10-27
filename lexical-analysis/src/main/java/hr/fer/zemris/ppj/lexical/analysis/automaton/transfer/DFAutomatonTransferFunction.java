package hr.fer.zemris.ppj.lexical.analysis.automaton.transfer;

import java.util.HashSet;
import java.util.Set;

public class DFAutomatonTransferFunction extends FAutomatonTransferFunction {

    public DFAutomatonTransferFunction(Set<DeterministicTransition> transitions) {
        super(new HashSet<FAutomatonTransition>(transitions));
    }

}
