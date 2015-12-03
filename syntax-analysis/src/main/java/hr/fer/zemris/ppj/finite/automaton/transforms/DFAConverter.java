package hr.fer.zemris.ppj.finite.automaton.transforms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import hr.fer.zemris.ppj.finite.automaton.DFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.interfaces.AutomatonTransform;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.interfaces.TransferFunction;
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

    private final Map<Set<State>, State> newStates = new HashMap<>();

    private State stateExample; // Used for creating new instances of the states.
    private final Map<State, Map<Input, Set<State>>> oldTransitions = new HashMap<>();
    // private Map<State, Set<State>> eClosures = new HashMap<>();
    private List<Set<State>> eClosures;

    // Used to build the automaton after transformation
    private Set<State> states;
    private final Set<State> acceptStates = new HashSet<>();
    private Set<Input> alphabet;
    private final Set<DeterministicTransition> transitions = new HashSet<>();
    private State startState;

    @Override
    public DFAutomaton transform(final ENFAutomaton source) {
        // System.out.println(source.getStates().size());
        stateExample = source.getStartState();
        alphabet = source.getAlphabet();

        final List<Input> tempAlphabet = new ArrayList<>(alphabet);
        // tempAlphabet.sort(null);

        final TransferFunction function = source.getTransferFunction();

        unrollTransitions(source.getStates(), function);
        eClosures = calculateEClosures(source.getStates());

        final Queue<Set<State>> unprocessed = new ArrayDeque<>(); // Queue is used instead of the stack to get state
                                                                  // numbers
        // that are easier to check by hand

        final Set<State> closure = eClosure(source.getStartState());
        startState = getState(closure);
        newStates.put(closure, startState);
        unprocessed.add(closure);

        while (!unprocessed.isEmpty()) {
            final Set<State> current = unprocessed.poll();
            // System.out.println(newStates.size() + " " + System.currentTimeMillis());

            final State newState = getState(current);

            if (acceptingDFAState(current, source)) {
                acceptStates.add(newState);
            }

            for (final Input symbol : tempAlphabet) {
                final Set<State> newClosure = transition(current, symbol);
                if (!newClosure.isEmpty()) {
                    if (!newStates.containsKey(newClosure) && !unprocessed.contains(newClosure)) {
                        unprocessed.add(newClosure);
                    }
                    final State transitionState = getState(newClosure);
                    transitions.add(new DeterministicTransition(newState, transitionState, symbol));
                    newStates.put(newClosure, transitionState);
                }
            }
        }

        states = new HashSet<>(newStates.values());

        return new DFAutomaton(states, acceptStates, alphabet, new DFAutomatonTransferFunction(transitions),
                startState);
    }

    private void unrollTransitions(final Set<State> states, final TransferFunction function) {
        for (final State state : states) {
            final Map<Input, Set<State>> stateTransitions = new HashMap<>();
            for (final Transition transition : function.getTransitionsFromState(state)) {
                Set<State> nextStates = stateTransitions.get(transition.getInput());
                if (nextStates == null) {
                    nextStates = new HashSet<>();
                }
                nextStates.add(transition.getNewState());
                stateTransitions.put(transition.getInput(), nextStates);
            }
            oldTransitions.put(state, stateTransitions);
        }
    }

    private List<Set<State>> calculateEClosures(final Set<State> states) {
        final List<Set<State>> closureList = new ArrayList<>(states.size());
        for (int i = 0; i < states.size(); i++) { // Who thought this was a good idea?
            closureList.add(null);
        }

        for (final State state : states) {
            final Set<State> closure = new HashSet<>();
            final Stack<State> stack = new Stack<>();

            closure.add(state);
            stack.push(state);
            while (!stack.isEmpty()) {
                final State top = stack.pop();
                final Map<Input, Set<State>> stateTransitions = oldTransitions.get(top);
                if (stateTransitions != null) {
                    final Set<State> epsilonStates = stateTransitions.get(null);
                    if (epsilonStates != null) {
                        for (final State eState : epsilonStates) {
                            if (!closure.contains(eState)) {
                                closure.add(eState);
                                stack.push(eState);
                            }
                        }
                    }
                }
            }
            closureList.set(Integer.valueOf(state.getId()), closure);
        }
        return closureList;
    }

    private State getState(final Set<State> states) {
        State state = newStates.get(states);
        if (state == null) {
            state = stateExample.newInstance(String.valueOf(newStates.size())).combine(states);
        }
        return state;
    }

    private Set<State> eClosure(final State state) {
        return eClosures.get(Integer.valueOf(state.getId()));
    }

    private Set<State> eClosure(final Set<State> states) {
        final Set<State> closure = new HashSet<>();
        for (final State state : states) {
            closure.addAll(eClosure(state));
        }
        return closure;
    }

    private Set<State> transition(final Set<State> states, final Input symbol) {
        final Set<State> closure = new HashSet<>();
        for (final State state : states) {
            if (oldTransitions.containsKey(state)) {
                final Map<Input, Set<State>> stateTransitions = oldTransitions.get(state);
                if (stateTransitions.containsKey(symbol)) {
                    closure.addAll(stateTransitions.get(symbol));
                }
            }
        }
        return eClosure(closure);
    }

    private boolean acceptingDFAState(final Set<State> states, final ENFAutomaton source) {
        for (final State accept : source.getAcceptStates()) {
            if (states.contains(accept)) {
                return true;
            }
        }
        return false;
    }
}
