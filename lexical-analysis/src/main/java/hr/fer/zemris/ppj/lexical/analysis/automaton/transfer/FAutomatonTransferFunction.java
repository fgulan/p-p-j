package hr.fer.zemris.ppj.lexical.analysis.automaton.transfer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.TransferFunction;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Transition;

import static hr.fer.zemris.ppj.lexical.analysis.automaton.Automatons.*;

public abstract class FAutomatonTransferFunction implements TransferFunction {

    private final Set<FAutomatonTransition> transitions = new HashSet<>();
    private final Set<FAutomatonTransition> epsilonTransitions = new HashSet<>();
    private final Set<FAutomatonTransition> normalTransitions = new HashSet<>();

    public FAutomatonTransferFunction(Set<FAutomatonTransition> transitions) {
        this.transitions.addAll(transitions);
        this.transitions.forEach(new Consumer<FAutomatonTransition>() {

            @Override
            public void accept(FAutomatonTransition t) {
                if (t.isEpsilonTransition()) {
                    epsilonTransitions.add(t);
                }
                else {
                    normalTransitions.add(t);
                }
            }
        });
    }

    @Override
    public boolean hasTransition(Transition transition) {
        return !getTransitions().contains(transition);
    }

    @Override
    public boolean hasTransition(State oldState, State newState, Input input) {
        return !getTransitions(oldState, newState, input).isEmpty();
    }

    @Override
    public Set<Transition> getTransitions() {
        return new HashSet<>(getTransitions(null, null, null));
    }

    @Override
    public Set<Transition> getTransitions(State oldState, State newState, Input input) {
        return new HashSet<>(findMatching(oldState, newState, input, transitions));
    }

    @Override
    public Set<State> getNewStates(Set<State> currentStates, Input input) {
        Set<State> newStates = new HashSet<>(currentStates);
        if (input != null) {
            newStates = applyTransition(newStates, input);
        }

        newStates = applyEpsilonTransition(newStates);

        return newStates;
    }

    private Set<Transition> findMatching(State oldState, State newState, Input input,
            Set<FAutomatonTransition> transitions) {
        Set<Transition> found = new HashSet<>();
        
        for (Transition transition: transitions) {
            boolean inputBool = input != null && !input.equals(transition.getInput()) ;
            boolean oldBool = oldState != null && !oldState.equals(transition.getOldState());
            boolean newBool = newState != null && !newState.equals(transition.getNewState());
            
            if (inputBool || oldBool || newBool) {
                continue;
            }
            found.add(transition);
        }
        return found;
    }

    private Set<State> applyEpsilonTransition(Set<State> currentStates) {

        if (currentStates.isEmpty()) {
            return new HashSet<>();
        }

        if (epsilonTransitions.isEmpty()) {
            return currentStates;
        }

        Set<State> returnStates = new HashSet<>(currentStates);

        while (true) {
            Set<State> newStates = new HashSet<>();

            int oldCount = returnStates.size();
            for (State currentState : returnStates) {
                newStates
                        .addAll(extractStates(findMatching(currentState, null, null, epsilonTransitions), false, true));
            }

            returnStates.addAll(newStates);
            int newCount = returnStates.size();
            if (newCount == oldCount) {
                break;
            }
        }

        return returnStates;
    }

    private Set<State> applyTransition(Set<State> currentStates, Input input) {
        if (currentStates.isEmpty()) {
            return new HashSet<>();
        }

        Set<State> newStates = new HashSet<>();
        for (State currentState : currentStates) {
            newStates.addAll(extractStates(findMatching(currentState, null, input, normalTransitions), false, true));
        }

        return newStates;
    }

}
