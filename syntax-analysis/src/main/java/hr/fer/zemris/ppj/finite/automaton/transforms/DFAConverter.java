package hr.fer.zemris.ppj.finite.automaton.transforms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import hr.fer.zemris.ppj.finite.automaton.DFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.interfaces.AutomatonTransform;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Transition;
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

    private Map<Set<State>, State> newStates = new HashMap<>();
    private Map<State, Map<Input, Set<State>>> newTransitions = new HashMap<>();

    private State stateExample; // Used for creating new instances of the states.
    private Map<State, Map<Input, Set<State>>> oldTransitions = new HashMap<>();
    private Map<State, Set<State>> eClosures = new HashMap<>();

    // Used to build the automaton after transformation
    private Set<State> states = new HashSet<>();
    private Set<State> acceptStates = new HashSet<>();
    private Set<Input> alphabet = new HashSet<>();
    private Set<DeterministicTransition> transitions = new HashSet<>();
    private State startState;

    @Override
    public DFAutomaton transform(final ENFAutomaton source) {
        System.out.println(source.getStates().size());
        stateExample = source.getStartState();
        List<Input> tempAlphabet = new ArrayList<>(source.getAlphabet());
        Collections.sort(tempAlphabet);

        unrollTransitions(source.getTransferFunction().getTransitions());
        calculateEClosures(source.getStates());

        Set<State> closure = eClosures.get(source.getStartState());

        Queue<Set<State>> unprocessed = new ArrayDeque<>(); // Queue is used instead of the stack to get state numbers
                                                            // that are easier to check by hand
        unprocessed.add(closure);

        while (!unprocessed.isEmpty()) {
            Set<State> current = unprocessed.poll();
            System.out.println("Found new state: " + newStates.size());
            State newState = stateExample.newInstance(String.valueOf(newStates.size())).combine(current);
            newStates.put(current, newState);

            if (startState == null) {
                startState = newState;
            }

            Map<Input, Set<State>> stateTransitions = new HashMap<>();

            for (Input symbol : tempAlphabet) {
                Set<State> newClosure = transition(current, symbol);
                if (!newClosure.isEmpty()) {
                    if (!newStates.containsKey(newClosure) && !unprocessed.contains(newClosure)) {
                        unprocessed.add(newClosure);
                    }
                    stateTransitions.put(symbol, newClosure);
                }
            }
            newTransitions.put(newState, stateTransitions);
        }

        for (Set<State> newState : newStates.keySet()) {
            State DFAState = newStates.get(newState);
            states.add(DFAState);
            if (acceptingDFAState(newState, source)) {
                acceptStates.add(DFAState);
            }

            if (newTransitions.containsKey(DFAState)) {
                for (Entry<Input, Set<State>> transition : newTransitions.get(DFAState).entrySet()) {
                    transitions.add(new DeterministicTransition(DFAState, newStates.get(transition.getValue()),
                            transition.getKey()));
                }
            }
        }

        alphabet.addAll(source.getAlphabet());

        return new DFAutomaton(states, acceptStates, alphabet, new DFAutomatonTransferFunction(transitions),
                startState);
    }

    private void unrollTransitions(Set<Transition> transitions) {
        for (Transition transition : transitions) {
            Map<Input, Set<State>> stateTransitions = oldTransitions.containsKey(transition.getOldState())
                    ? oldTransitions.get(transition.getOldState()) : new HashMap<>();
            Set<State> inputTransitions = stateTransitions.containsKey(transition.getInput())
                    ? stateTransitions.get(transition.getInput()) : new HashSet<>();
            inputTransitions.add(transition.getNewState());
            stateTransitions.put(transition.getInput(), inputTransitions);
            oldTransitions.put(transition.getOldState(), stateTransitions);
        }
    }

    private void calculateEClosures(Set<State> states) {
        for (State state : states) {
            Set<State> closure = new HashSet<>();
            closure.add(state);
            Stack<State> stack = new Stack<>();
            stack.add(state);
            while (!stack.isEmpty()) {
                State top = stack.pop();
                if (oldTransitions.containsKey(top)) {
                    Map<Input, Set<State>> stateTransitions = oldTransitions.get(top);
                    if (stateTransitions.containsKey(null)) {
                        for (State eState : stateTransitions.get(null)) {
                            if (!closure.contains(eState)) {
                                closure.add(eState);
                                stack.push(eState);
                            }
                        }
                    }
                }
            }
            eClosures.put(state, closure);
        }
    }

    private Set<State> transition(Set<State> states, Input symbol) {
        Set<State> closure = new HashSet<>();
        for (State state : states) {
            if (oldTransitions.containsKey(state)) {
                Map<Input, Set<State>> stateTransitions = oldTransitions.get(state);
                if (stateTransitions.containsKey(symbol)) {
                    closure.addAll(eClosure(stateTransitions.get(symbol)));
                }
            }
        }
        return closure;
    }

    private Set<State> eClosure(Set<State> states) {
        Set<State> closure = new HashSet<>();
        for (State state : states) {
            closure.addAll(eClosures.get(state));
        }
        return closure;
    }

    private boolean acceptingDFAState(Set<State> states, ENFAutomaton source) {
        for (State accept : source.getAcceptStates()) {
            if (states.contains(accept)) {
                return true;
            }
        }
        return false;
    }
}
