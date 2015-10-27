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
    public DFAutomaton transform(final ENFAutomaton source) {

        final Set<Input> inputs = source.getInputs();
        final Set<Transition> transitions = source.getTransferFunction().getTransitions();

        final Set<State> newState = getNewStartState(source);
        final State DFAStartState = getNewDFAStateName(newState);
        states.add(DFAStartState);
        if (isAcceptStateDFA(newState, source)) {
            acceptStates.add(DFAStartState);
        }

        constructErrorState(inputs);
        newStateTransitions(newState, source);

        final DFAutomatonTransferFunction function = new DFAutomatonTransferFunction(newTransitions);

        return new DFAutomaton(states, acceptStates, inputs, function, DFAStartState);
    }

    public Set<State> getNewStates(final Set<State> currentStates, final TransferFunction transferfunction,
            final Input input) {
        return transferfunction.getNewStates(currentStates, input);
    }

    public Set<State> getEpsilonClosure(final Set<State> currentStates, final TransferFunction transferFunction) {
        Set<State> epsilonClosure = new HashSet<>();
        epsilonClosure = transferFunction.getNewStates(currentStates, null);
        return epsilonClosure;
    }

    public Set<State> getNewStartState(final ENFAutomaton source) {
        final Set<State> newStateName = new TreeSet<State>();
        Set<State> newState = new HashSet<>();
        newState.add(source.getStartState());
        newState = getEpsilonClosure(newState, source.getTransferFunction());
        newStateName.addAll(newState);
        return newState;
    }

    public State getNewDFAStateName(final Set<State> newStateName) {
        String Name = "";
        for (final State state : newStateName) {
            Name = Name + state.getId();
        }
        final State newName = new BasicState(Name);
        return newName;
    }

    public Set<State> getNewDFAState(final ENFAutomaton source, final Set<State> current) {
        final Set<State> newStateName = new TreeSet<State>();
        Set<State> newState = new HashSet<>();
        newState = getEpsilonClosure(current, source.getTransferFunction());
        newStateName.addAll(newState);
        return newStateName;
    }

    private void constructErrorState(final Set<Input> inputs) {
        errorState = new BasicState("ERR");
        for (final Input input : inputs) {
            newTransitions.add(new DeterministicTransition(errorState, errorState, input));
        }
    }

    public boolean isAcceptStateDFA(final Set<State> states, final ENFAutomaton source) {
        final Set<State> uncheckedStates = new HashSet<>(states);
        uncheckedStates.retainAll(source.getAcceptStates());
        return !uncheckedStates.isEmpty();
    }

    public void newStateTransitions(final Set<State> oldState, final ENFAutomaton source) {
        final Set<Input> inputs = source.getInputs();
        Set<State> newState = new HashSet<>();
        boolean isNew = true;

        while (isNew) {
            isNew = false;
            for (final Input input : inputs) {
                newState = getNewStates(oldState, source.getTransferFunction(), input);
                newState = getEpsilonClosure(newState, source.getTransferFunction());
                newState = getNewDFAState(source, newState);
                final State stateName = newState.isEmpty() ? errorState : getNewDFAStateName(newState);
                if (!states.contains(stateName)) {
                    isNew = true;
                    states.add(stateName);
                    if (isAcceptStateDFA(newState, source)) {
                        acceptStates.add(stateName);
                    }
                    final State oldStateName = getNewDFAStateName(oldState);
                    final DeterministicTransition transition =
                            new DeterministicTransition(oldStateName, stateName, input);
                    newTransitions.add(transition);
                    newStateTransitions(newState, source);
                }
            }
        }

    }

}
