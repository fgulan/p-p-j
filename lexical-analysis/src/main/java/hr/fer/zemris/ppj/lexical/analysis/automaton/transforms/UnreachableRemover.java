package hr.fer.zemris.ppj.lexical.analysis.automaton.transforms;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.lexical.analysis.automaton.DFAutomaton;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.AutomatonTransform;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transfer.DFAutomatonTransferFunction;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transfer.DeterministicTransition;

public class UnreachableRemover implements AutomatonTransform<DFAutomaton, DFAutomaton> {

    @Override
    public DFAutomaton transform(DFAutomaton source) {

        Set<State> reachable = new HashSet<>();
        reachable.add(source.getStartState());
        Set<Input> inputs = source.getInputs();
        Set<Transition> transitions = source.getTransferFunction().getTransitions();

        while (true) {
            int oldReachableCount = reachable.size();

            for (Input input : inputs) {

                Set<State> moreStates = new HashSet<>();

                for (Transition transition : transitions) {
                    for (State currentState : reachable) {
                        if (currentState.equals(transition.getOldState()) && input.equals(transition.getInput())) {
                            moreStates.add(transition.getNewState());
                        }
                    }
                }

                reachable.addAll(moreStates);
            }

            int newReachableCount = reachable.size();

            if (newReachableCount == oldReachableCount) {
                break;
            }
        }

        Set<DeterministicTransition> usefulTransitions = new HashSet<>();
        Set<State> reachAccept = new HashSet<>();

        for (State currentState : reachable) {
            for (Input input : inputs) {
                for (Transition transition : transitions) {
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

        DFAutomatonTransferFunction usefulFunction = new DFAutomatonTransferFunction(usefulTransitions);

        return new DFAutomaton(reachable, reachAccept, inputs, usefulFunction, source.getStartState());
    }

}
