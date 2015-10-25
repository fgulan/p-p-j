package hr.fer.zemris.ppj.lexical.analysis.automaton.transforms;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.ppj.lexical.analysis.automaton.BasicState;
import hr.fer.zemris.ppj.lexical.analysis.automaton.DFAutomaton;
import hr.fer.zemris.ppj.lexical.analysis.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.AutomatonTransform;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.TransferFunction;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transfer.DFAutomatonTransferFunction;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transfer.DeterministicTransition;

public class ENFAtoDFA implements AutomatonTransform<ENFAutomaton, DFAutomaton> {

    Set<State> states = new HashSet<>();
    Set<State> acceptStates = new HashSet<>();
    Set<DeterministicTransition> newTransitions = new HashSet<>();

    private State errorState;

    @Override
    public DFAutomaton transform(ENFAutomaton source) {

        Set<Input> inputs = source.getInputs();
        Set<Transition> transitions = source.getTransferFunction().getTransitions();

        Set<State> newState = getNewStartState(source);
        State DFAStartState = getNewDFAStateName(newState);
        states.add(DFAStartState);
        if (isAcceptStateDFA(newState, source)) {
            acceptStates.add(DFAStartState);
        }

        constructErrorState(inputs);
        newStateTransitions(newState, source);

        DFAutomatonTransferFunction function = new DFAutomatonTransferFunction(newTransitions);

        return new DFAutomaton(states, acceptStates, inputs, function, DFAStartState);
    }

    public Set<State> getNewStates(Set<State> currentStates, TransferFunction transferfunction, Input input) {
        return transferfunction.getNewStates(currentStates, input);
    }

    public Set<State> getEpsilonClosure(Set<State> currentStates, TransferFunction transferFunction) {
        Set<State> epsilonClosure = new HashSet<>();
        epsilonClosure = transferFunction.getNewStates(currentStates, null);
        return epsilonClosure;
    }

    public Set<State> getNewStartState(ENFAutomaton source) {
        Set<State> newStateName = new TreeSet<State>();
        Set<State> newState = new HashSet<>();
        newState.add(source.getStartState());
        newState = getEpsilonClosure(newState, source.getTransferFunction());
        newStateName.addAll(newState);
        return newState;
    }

    public State getNewDFAStateName(Set<State> newStateName) {
        String Name = "";
        for (State state : newStateName) {
            Name = Name + state.getId();
        }
        State newName = new BasicState(Name);
        return newName;
    }

    public Set<State> getNewDFAState(ENFAutomaton source, Set<State> current) {
        Set<State> newStateName = new TreeSet<State>();
        Set<State> newState = new HashSet<>();
        newState = getEpsilonClosure(current, source.getTransferFunction());
        newStateName.addAll(newState);
        return newStateName;
    }

    private void constructErrorState(Set<Input> inputs) {
        errorState = new BasicState("ERR");
        for (Input input : inputs) {
            newTransitions.add(new DeterministicTransition(errorState, errorState, input));
        }
    }

    public boolean isAcceptStateDFA(Set<State> states, ENFAutomaton source) {
        Set<State> uncheckedStates = new HashSet<>(states);
        uncheckedStates.retainAll(source.getAcceptStates());
        return !uncheckedStates.isEmpty();
    }

    public void newStateTransitions(Set<State> oldState, ENFAutomaton source) {
        Set<Input> inputs = source.getInputs();
        Set<State> newState = new HashSet<>();
        boolean isNew = true;

        while (isNew) {
            isNew = false;
            for (Input input : inputs) {
                newState = getNewStates(oldState, source.getTransferFunction(), input);
                newState = getEpsilonClosure(newState, source.getTransferFunction());
                newState = getNewDFAState(source, newState);
                State stateName = newState.isEmpty() ? errorState : getNewDFAStateName(newState);
                if (!states.contains(stateName)) {
                    isNew = true;
                    states.add(stateName);
                    if (isAcceptStateDFA(newState, source)) {
                        acceptStates.add(stateName);
                    }
                    State oldStateName = getNewDFAStateName(oldState);
                    DeterministicTransition transition = new DeterministicTransition(oldStateName, stateName, input);
                    newTransitions.add(transition);
                    newStateTransitions(newState, source);
                }
            }
        }

    }

}
