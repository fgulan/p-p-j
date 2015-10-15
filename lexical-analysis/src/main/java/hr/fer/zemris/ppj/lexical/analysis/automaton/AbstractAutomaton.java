package hr.fer.zemris.ppj.lexical.analysis.automaton;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Automaton;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.TransferFunction;

public abstract class AbstractAutomaton implements Automaton {
    
    private final Set<State> states;
    private final Set<State> acceptStates;
    private final Set<State> currentStates;
    private final Set<Input> inputs;
    private final TransferFunction transferFunction;
    private final State startState;
    
    public AbstractAutomaton(Set<State> states, Set<State> acceptStates, Set<State> currentStates, Set<Input> inputs,
            TransferFunction transferFunction, State startState) {
        super();
        this.states = states;
        this.acceptStates = acceptStates;
        this.currentStates = currentStates;
        this.inputs = inputs;
        this.transferFunction = transferFunction;
        this.startState = startState;
    }

    
    public Set<State> getStates() {
        return new HashSet<>(states);
    }

    
    public Set<State> getAcceptStates() {
        return new HashSet<>(acceptStates);
    }

    
    public Set<State> getCurrentStates() {
        return new HashSet<>(currentStates);
    }

    
    public Set<Input> getInputs() {
        return new HashSet<>(inputs);
    }

    
    public TransferFunction getTransferFunction() {
        return transferFunction;
    }

    
    public State getStartState() {
        return startState;
    }
}
