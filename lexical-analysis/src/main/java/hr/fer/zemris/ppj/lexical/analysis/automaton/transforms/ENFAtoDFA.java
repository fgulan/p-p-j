package hr.fer.zemris.ppj.lexical.analysis.automaton.transforms;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.ppj.lexical.analysis.automaton.DFAutomaton;
import hr.fer.zemris.ppj.lexical.analysis.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.lexical.analysis.automaton.BasicState;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.AutomatonTransform;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.TransferFunction;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transfer.DFAutomatonTransferFunction;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transfer.DeterministicTransition;


public class ENFAtoDFA implements AutomatonTransform<ENFAutomaton, DFAutomaton> {
	
	Set<State> DFAState = new HashSet<>();
    Set<State> acceptState = new HashSet<>();
    Set<DeterministicTransition> newTransitions = new HashSet<>();
	
	@Override
	public DFAutomaton transform(ENFAutomaton source) {
		
        Set<Input> inputs = source.getInputs();
        Set<Transition> transitions = source.getTransferFunction().getTransitions();
        
        Set<State> newState = getNewStartState (source);
        State DFAStartState = getNewDFAStateName (newState);
        DFAState.add(DFAStartState);
        if (isAcceptStateDFA(newState, source)) acceptState.add(DFAStartState);
        
        newStateTransitions (newState, source);
        
        DFAutomatonTransferFunction function = new DFAutomatonTransferFunction(newTransitions);
        
        return new DFAutomaton(DFAState, acceptState, inputs, function, DFAStartState);
	}
	
	public Set<State> getNewStates (Set<State> currentStates, TransferFunction transferfunction, Input input) {
		return transferfunction.getNewStates(currentStates, input);
	}
	
	public Set<State> getEpsilonClosure(Set<State> currentStates, TransferFunction transferFunction){
		Set<State> epsilonClosure = new HashSet<>();
		epsilonClosure = transferFunction.getNewStates(currentStates, null);
		return epsilonClosure;
	}
	
	public Set<State> getNewStartState(ENFAutomaton source) {
		Set<State> newStateName = new TreeSet<State>();
		Set<State> newState = new HashSet<>();
		newState.add(source.getStartState());
		newState = getEpsilonClosure (newState, source.getTransferFunction());
        newStateName.addAll(newState);
        return newState;
	}
	
	public State getNewDFAStateName (Set<State> newStateName){
		String Name = null;
        for (State state : newStateName) {
        	Name = Name + state.getId();	
        }
        State newName = new BasicState(Name);
        return newName;
	}
	
	public Set<State> getNewDFAState(ENFAutomaton source, Set<State> current) {
		Set<State> newStateName = new TreeSet<State>();
		Set<State> newState = new HashSet<>();
		newState = getEpsilonClosure (current, source.getTransferFunction());
        newStateName.addAll(newState);
		return newStateName;
	}
	
	public boolean isAcceptStateDFA (Set<State> states, ENFAutomaton source) {
		for (State state : states) if (source.isAcceptState(state)) return true;
		return false;
	}
	
	public void newStateTransitions (Set<State> oldState, ENFAutomaton source) {
		Set<Input> inputs = source.getInputs();
		Set<State> newState = new HashSet<>();
		boolean isNew=true;
		
		while (isNew) {
        	isNew=false;
        	for (Input input : inputs) {
        		newState = getNewStates (oldState, source.getTransferFunction(), input);
        		newState = getEpsilonClosure (newState, source.getTransferFunction());
        		newState = getNewDFAState(source, newState);
        		State stateName = getNewDFAStateName (newState);
        		if (!DFAState.contains(stateName)) {
        			isNew = true;
        			DFAState.add(stateName);
        			if (isAcceptStateDFA(newState, source)) acceptState.add(stateName);
        			State oldStateName = getNewDFAStateName (oldState);
        			DeterministicTransition transition = new DeterministicTransition(oldStateName, stateName, input);
        			newTransitions.add(transition);
        			newStateTransitions (newState, source);
        		}
        	}
        }
		
	}

}

