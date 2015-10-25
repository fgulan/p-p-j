package hr.fer.zemris.ppj.lexical.analysis.automaton.transforms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.ppj.lexical.analysis.automaton.DFAutomaton;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.AutomatonTransform;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transfer.DFAutomatonTransferFunction;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transfer.DeterministicTransition;

public class EquivalentRemover implements AutomatonTransform<DFAutomaton, DFAutomaton> {

    private Map<State, List<StatePair>> statePairsIndex = new HashMap<>();
    private Map<State, List<Transition>> oldStateTransitionsIndex = new HashMap<>();
    private Map<State, List<Transition>> newStateTransitionsIndex = new HashMap<>();
    
    public static class StatePair{
        private State first;
        private State second;
        private boolean marked;
        private Set<StatePair> linkedPairs = new HashSet<>();
        
        public StatePair(State first, State second) {
            super();
            
            if (first == null || second == null){
                throw new IllegalArgumentException();
            }
            
            this.first = first;
            this.second = second;
        }
        
        public void mark(){
            marked = true;
            for (StatePair pair: linkedPairs){
                if(!pair.marked){
                    pair.mark();
                }
            }
        }
        
        public void unMark(){
            marked = false;
            for (StatePair pair: linkedPairs){
                pair.unMark();
            }
        }
        
        public void addLinkedPair(StatePair pair){
            linkedPairs.add(pair);
        }

        public State getFirst() {
            return first;
        }


        public State getSecond() {
            return second;
        }
        
        public boolean contains(State state){
            return first.equals(state) || second.equals(state);
        }
        
        public String toString(){
            return first + " " + second + " " + marked;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((first == null) ? 0 : first.hashCode())
                    + ((second == null) ? 0 : second.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof StatePair)) {
                return false;
            }
            StatePair other = (StatePair) obj;

            if (this.first == null && (other.first == null || other.second == null)){
                return true;
            }
            
            if (other.first == null && (this.first == null || this.second == null)){
                return true;
            }
            
            if (this.first.equals(other.first) && this.second.equals(other.second)){
                return true;
            }
            
            if (this.first.equals(other.second) && this.second.equals(other.first)){
                return true;
            }
            
            return false;
        }
    }
    
    private Set<StatePair> pairs = new HashSet<>();
    
    @Override
    public DFAutomaton transform(
            DFAutomaton source) {

        Set<State> states = new TreeSet<>(source.getStates()); // use TreeSet to order states
        Set<State> candidates = new HashSet<>(source.getStates());
        
        fillPairs(source);
        markPairs(source);
        populateIndexes(source);
        
        Set<DeterministicTransition> rewiredTransitions = new HashSet<>();
        State startState = source.getStartState();
        
        for (State state: states){
            // in case the visited state is already removed
            if (!candidates.contains(state)){
                continue;
            }
            
            List<StatePair> pairs = statePairsIndex.get(state);
            for (StatePair pair: pairs){
                if (!pair.marked){
                    State removed = null;
                    
                    /*
                     * if tree set is used for 'states', this will ensure that if two states are equal
                     * the one which is visited first will be kept and the other one will be removed.
                     */
                    if (state.equals(pair.first)){
                        removed = pair.second;
                    } else {
                        removed = pair.first;
                    }
                    
                    candidates.remove(removed);
                    
                    /*
                     *  if the initial state has been removed, the new initial state
                     *  of the automaton is the state which is equivalent to the old initial state 
                     */
                    if (removed.equals(startState)){
                        startState = state;
                    }

                    // rewire transitions
                    for (State oldState : states) {
                        if (!candidates.contains(oldState)) {
                            continue;
                        }

                        List<Transition> oldStateTransitions = oldStateTransitionsIndex.get(oldState);
                        
                        for (Transition transition : oldStateTransitions) {
                            if (transition.getNewState().equals(removed)) {
                                /*
                                 * Adding a new normal transition with the same oldState and input as another normal
                                 * transition will remove that transition. This means that, when rewiring transitions,
                                 * previously rewired transitions which have the removed state as the new state will be
                                 * removed.
                                 */
                                rewiredTransitions.add(
                                        new DeterministicTransition(oldState, state, transition.getInput()));
                            }
                        }
                    }
                }
            }
        }
        
        Iterator<DeterministicTransition> iterator = rewiredTransitions.iterator();
        while(iterator.hasNext()){
            Transition transition = iterator.next();
            if(!candidates.contains(transition.getOldState())){
                iterator.remove();
            }
        }
        
        /*
         *  Useful transitions set is a union of rewired and remaining transitions.
         *  This is because rewired transitions is a set of normal transitions
         */
        Set<DeterministicTransition>usefulTransitions = new HashSet<>();
        usefulTransitions.addAll(rewiredTransitions);
        Set<State> acceptStates = new HashSet<>();
        Set<Input> inputs = source.getInputs();

        for (State currentState : candidates) {
            List<Transition> oldStateTransitions = oldStateTransitionsIndex.get(currentState);
            for (Input input : inputs) {
                for (Transition transition : oldStateTransitions) {
                    if (input.equals(transition.getInput())
                            && candidates.contains(transition.getNewState())) {
                        usefulTransitions.add((DeterministicTransition) transition);
                    }
                }
            }

            if (source.isAcceptState(currentState)){
                acceptStates.add(currentState);
            }
        }

        DFAutomatonTransferFunction usefulFunction = new DFAutomatonTransferFunction(usefulTransitions);
        return new DFAutomaton(candidates, acceptStates, inputs, usefulFunction, startState);
    }

    private void populateIndexes(DFAutomaton source) {
        Set<Transition> transitions = source.getTransferFunction().getTransitions();
        
        for (Transition transition: transitions){
            State oldState = transition.getOldState();
            State newState = transition.getNewState();
            
            if (oldStateTransitionsIndex.get(oldState) == null){
                List<Transition> transList= new ArrayList<>();
                oldStateTransitionsIndex.put(oldState, transList);
            }
            
            if (newStateTransitionsIndex.get(newState) == null){
                List<Transition> transList= new ArrayList<>();
                newStateTransitionsIndex.put(newState, transList);
            }
            
            oldStateTransitionsIndex.get(oldState).add(transition);
            newStateTransitionsIndex.get(newState).add(transition);
        }
        
        for (StatePair pair: pairs){
            if (pair.first.equals(pair.second)){
                continue;
            }
            
            if (statePairsIndex.get(pair.first) == null){
                statePairsIndex.put(pair.first, new ArrayList<>());
            }
            
            if (statePairsIndex.get(pair.second) == null){
                statePairsIndex.put(pair.second, new ArrayList<>());
            }
            
            statePairsIndex.get(pair.first).add(pair);
            statePairsIndex.get(pair.second).add(pair);
        }
    }

    private StatePair findPair(StatePair pair){
        if (!pairs.contains(pair)){
            return null;
        }
        
        for (StatePair anotherPair: pairs){
            if (anotherPair.equals(pair)){
                return anotherPair;
            }
        }
        
        return null;
    }
    
    private void markPairs(DFAutomaton automaton) {
        
        Set<Input> inputs = automaton.getInputs();
        
        for (Input input: inputs){
            for (StatePair pair: pairs){
                State firstNext = automaton.getTransition(pair.first, input).getNewState();
                State secondNext = automaton.getTransition(pair.second, input).getNewState();
                
                if (firstNext == null || secondNext == null){
                    continue;
                }
                
                StatePair nextPair = findPair(new StatePair(firstNext, secondNext));
                
                if (nextPair == null){
                    continue;
                }
                
                if (nextPair.marked){
                    pair.mark();
                } else {
                    for (Input anotherInput: inputs){
                        firstNext =  automaton.getTransition(pair.first, anotherInput).getNewState();
                        secondNext = automaton.getTransition(pair.second, anotherInput).getNewState();
                        
                        if (!firstNext.equals(secondNext)){
                            nextPair = findPair(new StatePair(firstNext, secondNext));
                            nextPair.addLinkedPair(pair);
                        }
                    }
                }
            }
        }
    }

    private void fillPairs(DFAutomaton automaton) {
        Set<State> machineStates = automaton.getStates();
        
        for(State currentState: machineStates){
            for(State anotherState: machineStates){
                StatePair pair = new StatePair(currentState, anotherState);

                if(!pairs.contains(pair)){
                    boolean firstIsAcceptable = automaton.isAcceptState(pair.getFirst());
                    boolean secondIsAcceptable = automaton.isAcceptState(pair.getSecond());

                    pairs.add(pair);
                    
                    if (firstIsAcceptable ^ secondIsAcceptable){
                        pair.mark();
                    }
                }
            }
        }
    }
}
