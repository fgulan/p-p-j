package hr.fer.zemris.ppj.finite.automaton.transforms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.ppj.finite.automaton.DFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.interfaces.AutomatonTransform;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.finite.automaton.transfer.DFAutomatonTransferFunction;
import hr.fer.zemris.ppj.finite.automaton.transfer.DeterministicTransition;

/**
 * <code>EquivalentRemover</code> is a automaton transformer which removes equivalents states from the deterministic
 * finite automaton.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class EquivalentRemover implements AutomatonTransform<DFAutomaton, DFAutomaton> {

    private final Map<State, List<StatePair>> statePairsIndex = new HashMap<>();
    private final Map<State, List<Transition>> oldStateTransitionsIndex = new HashMap<>();
    private final Map<State, List<Transition>> newStateTransitionsIndex = new HashMap<>();

    /*
     * Element of the table used by the algorithm.
     */
    private static class StatePair {

        private final State first;
        private final State second;
        private boolean marked;
        private final Set<StatePair> linkedPairs = new HashSet<>();

        public StatePair(final State first, final State second) {
            super();

            if ((first == null) || (second == null)) {
                throw new IllegalArgumentException();
            }

            this.first = first;
            this.second = second;
        }

        public void mark() {
            marked = true;
            for (final StatePair pair : linkedPairs) {
                if (!pair.marked) {
                    pair.mark();
                }
            }
        }

        public void addLinkedPair(final StatePair pair) {
            linkedPairs.add(pair);
        }

        public State getFirst() {
            return first;
        }

        public State getSecond() {
            return second;
        }

        @Override
        public String toString() {
            return first + " " + second + " " + marked;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = (prime * result) + ((first == null) ? 0 : first.hashCode())
                    + ((second == null) ? 0 : second.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof StatePair)) {
                return false;
            }
            final StatePair other = (StatePair) obj;

            if ((first == null) && ((other.first == null) || (other.second == null))) {
                return true;
            }

            if ((other.first == null) && ((first == null) || (second == null))) {
                return true;
            }

            if (first.equals(other.first) && second.equals(other.second)) {
                return true;
            }

            if (first.equals(other.second) && second.equals(other.first)) {
                return true;
            }

            return false;
        }
    }

    private final Set<StatePair> pairs = new HashSet<>();

    @Override
    public DFAutomaton transform(final DFAutomaton source) {

        final Set<State> states = new TreeSet<>(source.getStates()); // use TreeSet to order states
        final Set<State> candidates = new HashSet<>(source.getStates());

        fillPairs(source);
        markPairs(source);
        populateIndexes(source);

        final Set<DeterministicTransition> rewiredTransitions = new HashSet<>();
        State startState = source.getStartState();

        for (final State state : states) {
            // in case the visited state is already removed
            if (!candidates.contains(state)) {
                continue;
            }

            final List<StatePair> pairs = statePairsIndex.get(state);
            if (pairs == null) {
                continue;
            }
            for (final StatePair pair : pairs) {
                if (!pair.marked) {
                    State removed = null;

                    /*
                     * if tree set is used for 'states', this will ensure that if two states are equal the one which is
                     * visited first will be kept and the other one will be removed.
                     */
                    if (state.equals(pair.first)) {
                        removed = pair.second;
                    }
                    else {
                        removed = pair.first;
                    }

                    candidates.remove(removed);

                    /*
                     * if the initial state has been removed, the new initial state of the automaton is the state which
                     * is equivalent to the old initial state
                     */
                    if (removed.equals(startState)) {
                        startState = state;
                    }

                    // rewire transitions
                    for (final State oldState : states) {
                        if (!candidates.contains(oldState)) {
                            continue;
                        }

                        final List<Transition> oldStateTransitions = oldStateTransitionsIndex.get(oldState);

                        for (final Transition transition : oldStateTransitions) {
                            if (transition.getNewState().equals(removed)) {
                                /*
                                 * Adding a new normal transition with the same oldState and input as another normal
                                 * transition will remove that transition. This means that, when rewiring transitions,
                                 * previously rewired transitions which have the removed state as the new state will be
                                 * removed.
                                 */
                                rewiredTransitions
                                        .add(new DeterministicTransition(oldState, state, transition.getInput()));
                            }
                        }
                    }
                }
            }
        }

        final Iterator<DeterministicTransition> iterator = rewiredTransitions.iterator();
        while (iterator.hasNext()) {
            final Transition transition = iterator.next();
            if (!candidates.contains(transition.getOldState())) {
                iterator.remove();
            }
        }

        /*
         * Useful transitions set is a union of rewired and remaining transitions. This is because rewired transitions
         * is a set of normal transitions
         */
        final Set<DeterministicTransition> usefulTransitions = new HashSet<>();
        usefulTransitions.addAll(rewiredTransitions);
        final Set<State> acceptStates = new HashSet<>();
        final Set<Input> inputs = source.getAlphabet();

        for (final State currentState : candidates) {
            final List<Transition> oldStateTransitions = oldStateTransitionsIndex.get(currentState);
            for (final Input input : inputs) {
                for (final Transition transition : oldStateTransitions) {
                    if (input.equals(transition.getInput()) && candidates.contains(transition.getNewState())) {
                        usefulTransitions.add((DeterministicTransition) transition);
                    }
                }
            }

            if (source.isAcceptState(currentState)) {
                acceptStates.add(currentState);
            }
        }

        final DFAutomatonTransferFunction usefulFunction = new DFAutomatonTransferFunction(usefulTransitions);
        return new DFAutomaton(candidates, acceptStates, inputs, usefulFunction, startState);
    }

    private void populateIndexes(final DFAutomaton source) {
        final Set<Transition> transitions = source.getTransferFunction().getTransitions();

        for (final Transition transition : transitions) {
            final State oldState = transition.getOldState();
            final State newState = transition.getNewState();

            if (oldStateTransitionsIndex.get(oldState) == null) {
                final List<Transition> transList = new ArrayList<>();
                oldStateTransitionsIndex.put(oldState, transList);
            }

            if (newStateTransitionsIndex.get(newState) == null) {
                final List<Transition> transList = new ArrayList<>();
                newStateTransitionsIndex.put(newState, transList);
            }

            oldStateTransitionsIndex.get(oldState).add(transition);
            newStateTransitionsIndex.get(newState).add(transition);
        }

        for (final StatePair pair : pairs) {
            if (pair.first.equals(pair.second)) {
                continue;
            }

            if (statePairsIndex.get(pair.first) == null) {
                statePairsIndex.put(pair.first, new ArrayList<StatePair>());
            }

            if (statePairsIndex.get(pair.second) == null) {
                statePairsIndex.put(pair.second, new ArrayList<StatePair>());
            }

            statePairsIndex.get(pair.first).add(pair);
            statePairsIndex.get(pair.second).add(pair);
        }
    }

    private StatePair findPair(final StatePair pair) {
        if (!pairs.contains(pair)) {
            return null;
        }

        for (final StatePair anotherPair : pairs) {
            if (anotherPair.equals(pair)) {
                return anotherPair;
            }
        }

        return null;
    }

    private void markPairs(final DFAutomaton automaton) {

        final Set<Input> inputs = automaton.getAlphabet();

        for (final Input input : inputs) {
            for (final StatePair pair : pairs) {
                State firstNext = automaton.getTransition(pair.first, input).getNewState();
                State secondNext = automaton.getTransition(pair.second, input).getNewState();

                if ((firstNext == null) || (secondNext == null)) {
                    continue;
                }

                StatePair nextPair = findPair(new StatePair(firstNext, secondNext));

                if (nextPair == null) {
                    continue;
                }

                if (nextPair.marked) {
                    pair.mark();
                }
                else {
                    for (final Input anotherInput : inputs) {
                        firstNext = automaton.getTransition(pair.first, anotherInput).getNewState();
                        secondNext = automaton.getTransition(pair.second, anotherInput).getNewState();

                        if (!firstNext.equals(secondNext)) {
                            nextPair = findPair(new StatePair(firstNext, secondNext));
                            nextPair.addLinkedPair(pair);
                        }
                    }
                }
            }
        }
    }

    private void fillPairs(final DFAutomaton automaton) {
        final Set<State> machineStates = automaton.getStates();

        for (final State currentState : machineStates) {
            for (final State anotherState : machineStates) {
                final StatePair pair = new StatePair(currentState, anotherState);

                if (!pairs.contains(pair)) {
                    final boolean firstIsAcceptable = automaton.isAcceptState(pair.getFirst());
                    final boolean secondIsAcceptable = automaton.isAcceptState(pair.getSecond());

                    pairs.add(pair);

                    if (firstIsAcceptable ^ secondIsAcceptable) {
                        pair.mark();
                    }
                }
            }
        }
    }
}
