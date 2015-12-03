package hr.fer.zemris.ppj.finite.automaton.transforms;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.DFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.interfaces.AutomatonTransform;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.finite.automaton.transfer.DFAutomatonTransferFunction;
import hr.fer.zemris.ppj.finite.automaton.transfer.DeterministicTransition;

/**
 * <code>UnreachableRemover</code> is a automaton transformer which removes unreachable states from the deterministic
 * finite automaton.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class UnreachableRemover implements AutomatonTransform<DFAutomaton, DFAutomaton> {

    @Override
    public DFAutomaton transform(final DFAutomaton source) {

        final Set<State> reachable = new HashSet<>();
        reachable.add(source.getStartState());
        final Set<Input> inputs = source.getAlphabet();
        final Set<Transition> transitions = source.getTransferFunction().getTransitions();

        while (true) {
            final int oldReachableCount = reachable.size();

            for (final Input input : inputs) {

                final Set<State> moreStates = new HashSet<>();

                for (final Transition transition : transitions) {
                    for (final State currentState : reachable) {
                        if (currentState.equals(transition.getOldState()) && input.equals(transition.getInput())) {
                            moreStates.add(transition.getNewState());
                        }
                    }
                }

                reachable.addAll(moreStates);
            }

            final int newReachableCount = reachable.size();

            if (newReachableCount == oldReachableCount) {
                break;
            }
        }

        final Set<DeterministicTransition> usefulTransitions = new HashSet<>();
        final Set<State> reachAccept = new HashSet<>();

        for (final State currentState : reachable) {
            for (final Input input : inputs) {
                for (final Transition transition : transitions) {
                    if (currentState.equals(transition.getOldState()) && input.equals(transition.getInput())
                            && reachable.contains(transition.getNewState())) {
                        usefulTransitions.add((DeterministicTransition) transition);
                    }
                }
            }

            if (source.isAcceptState(currentState)) {
                reachAccept.add(currentState);
            }
        }

        final DFAutomatonTransferFunction usefulFunction = new DFAutomatonTransferFunction(usefulTransitions);

        return new DFAutomaton(reachable, reachAccept, inputs, usefulFunction, source.getStartState());
    }

}
