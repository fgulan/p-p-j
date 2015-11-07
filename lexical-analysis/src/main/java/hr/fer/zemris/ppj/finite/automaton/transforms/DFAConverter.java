package hr.fer.zemris.ppj.finite.automaton.transforms;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.ppj.finite.automaton.BasicState;
import hr.fer.zemris.ppj.finite.automaton.DFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.interfaces.AutomatonTransform;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.interfaces.TransferFunction;
import hr.fer.zemris.ppj.finite.automaton.transfer.DFAutomatonTransferFunction;
import hr.fer.zemris.ppj.finite.automaton.transfer.DeterministicTransition;

/**
 * <code>DFAConverter</code> is a automaton transformer which converts a nondeterminist
 *
 * @author Matea Sabolic
 * @author Jan Kelemen
 *
 * @version beta
 */
public class DFAConverter implements AutomatonTransform<ENFAutomaton, DFAutomaton> {

    Set<State> states = new HashSet<>();
    Set<State> acceptStates = new HashSet<>();
    Set<DeterministicTransition> newTransitions = new HashSet<>();
    Map<State, State> stateMap = new HashMap<>();
    

    private State errorState;

    @Override
    public DFAutomaton transform(final ENFAutomaton source) {

        final Set<Input> inputs = source.getAlphabet();

        final Set<State> newState = getNewStartState(source);
        final State DFAStartState = getNewDFAStateName(newState);
        final State stateName = new BasicState ("0");
        states.add(stateName);
        if (isAcceptStateDFA(newState, source)) {
            acceptStates.add(stateName);
        }
        
        stateMap.put(DFAStartState, stateName);

        
        constructErrorState(inputs);
        newStateTransitions(newState, source);

        final DFAutomatonTransferFunction function = new DFAutomatonTransferFunction(newTransitions);

        return new DFAutomaton(states, acceptStates, inputs, function, DFAStartState);
    }

    private Set<State> getNewStates(final Set<State> currentStates, final TransferFunction transferfunction,
            final Input input) {
        return transferfunction.getNewStates(currentStates, input);
    }

    private Set<State> getEpsilonClosure(final Set<State> currentStates, final TransferFunction transferFunction) {
        Set<State> epsilonClosure = new HashSet<>();
        epsilonClosure = transferFunction.getNewStates(currentStates, null);
        return epsilonClosure;
    }

    private Set<State> getNewStartState(final ENFAutomaton source) {
        final Set<State> newStateName = new TreeSet<State>();
        Set<State> newState = new HashSet<>();
        newState.add(source.getStartState());
        newState = getEpsilonClosure(newState, source.getTransferFunction());
        newStateName.addAll(newState);
        return newState;
    }

    private State getNewDFAStateName(final Set<State> newStateName) {
        String Name = "";
        for (final State state : newStateName) {
            Name = Name + state.getId() + "_";
        }
        final State newName = new BasicState(Name);
        return newName;
    }

    private Set<State> getNewDFAState(final ENFAutomaton source, final Set<State> current) {
        final Set<State> newStateName = new TreeSet<State>();
        Set<State> newState = new HashSet<>();
        newState = getEpsilonClosure(current, source.getTransferFunction());
        newStateName.addAll(newState);
        return newStateName;
    }

    private void constructErrorState(final Set<Input> inputs) {
        errorState = new BasicState("ERR");
        State errorStateName = new BasicState ("1");
        stateMap.put(errorState, errorStateName);
        for (final Input input : inputs) {
            newTransitions.add(new DeterministicTransition(errorState, errorState, input));
        }
    }

    private boolean isAcceptStateDFA(final Set<State> states, final ENFAutomaton source) {
        final Set<State> uncheckedStates = new HashSet<>(states);
        uncheckedStates.retainAll(source.getAcceptStates());
        return !uncheckedStates.isEmpty();
    }

    private void newStateTransitions(final Set<State> oldState, final ENFAutomaton source) {
        final Set<Input> inputs = source.getAlphabet();
        Set<State> newState = new HashSet<>();
        boolean isNew = true;

        while (isNew) {
            isNew = false;
            for (final Input input : inputs) {
                newState = getNewStates(oldState, source.getTransferFunction(), input);
                newState = getEpsilonClosure(newState, source.getTransferFunction());
                newState = getNewDFAState(source, newState);
                final State stateName = newState.isEmpty() ? errorState : getNewDFAStateName(newState);
                if (!stateMap.containsKey(stateName)) {
                    isNew = true;
                    final State stateNumName = new BasicState (String.valueOf(stateMap.size()));
                    stateMap.put(stateName, stateNumName);
                    states.add(stateNumName);
                    if (isAcceptStateDFA(newState, source)) {
                        acceptStates.add(stateNumName);
                    }
                    final State oldStateName = getNewDFAStateName(oldState);
                    final DeterministicTransition transition =
                            new DeterministicTransition(stateMap.get(oldStateName), stateNumName, input);
                    newTransitions.add(transition);
                    newStateTransitions(newState, source);
                }
                else {
                	final State oldStateName = getNewDFAStateName(oldState);
                	final DeterministicTransition transition =
                            new DeterministicTransition(stateMap.get(oldStateName), stateMap.get(stateName), input);
                	newTransitions.add(transition);
                }
            }
        }

    }

}
