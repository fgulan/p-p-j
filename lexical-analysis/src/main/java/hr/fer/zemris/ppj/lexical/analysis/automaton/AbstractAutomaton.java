package hr.fer.zemris.ppj.lexical.analysis.automaton;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Automaton;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.TransferFunction;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Transition;

public abstract class AbstractAutomaton implements Automaton {

    private final Set<State> states;
    private final Set<State> acceptStates;
    private Set<State> currentStates = new HashSet<>();
    private final Set<Input> inputs;

    private final TransferFunction transferFunction;
    private final State startState;
    private Input lastInput;

    public AbstractAutomaton(Set<State> states, Set<State> acceptStates, Set<Input> inputs,
            TransferFunction transferFunction, State startState) {
        super();
        this.states = states;
        this.acceptStates = acceptStates;
        this.inputs = inputs;
        this.transferFunction = transferFunction;
        this.startState = startState;

        currentStates.add(startState);
        currentStates = transferFunction.getNewStates(currentStates, null);
    }

    public AbstractAutomaton(Set<State> acceptStates, TransferFunction transferFunction, State startState) {
        this(extract(transferFunction, State.class), acceptStates, extract(transferFunction, Input.class),
                transferFunction, startState);
    }

    @Override
    public Set<State> getStates() {
        return new HashSet<>(states);
    }

    @Override
    public Set<State> getAcceptStates() {
        return new HashSet<>(acceptStates);
    }

    @Override
    public Set<State> getCurrentStates() {
        return new HashSet<>(currentStates);
    }

    @Override
    public Set<Input> getInputs() {
        return new HashSet<>(inputs);
    }

    @Override
    public TransferFunction getTransferFunction() {
        return transferFunction;
    }

    @Override
    public State getStartState() {
        return startState;
    }

    @Override
    public Input getLastInput() {
        return lastInput;
    }

    @Override
    public boolean hasState(State state) {
        return states.contains(state);
    }

    @Override
    public boolean isAcceptState(State state) {
        return acceptStates.contains(state);
    }

    @Override
    public boolean isCurrentState(State state) {
        return currentStates.contains(state);
    }

    @Override
    public void reset() {
        currentStates = new HashSet<>();
        currentStates.add(startState);
        lastInput = null;
        currentStates = transferFunction.getNewStates(currentStates, null);
    }

    @Override
    public boolean isAccepting() {
        Set<State> intersection = getCurrentStates();
        intersection.retainAll(acceptStates);

        return !intersection.isEmpty();
    }

    @Override
    public String toString() {
        Set<State> states = new TreeSet<>(this.states);
        Set<Input> inputs = new HashSet<>(this.inputs);
        Set<State> acceptStates = new TreeSet<>(this.acceptStates);
        Set<Transition> transitions = new HashSet<>(transferFunction.getTransitions());

        String result =
                formatCollection(states, " ") + "\n" + formatCollection(acceptStates, " ") + "\n" + startState.getId()
                        + "\n" + formatCollection(inputs, " ") + "\n" + formatCollection(transitions, "\n");

        return result;
    }

    @SuppressWarnings("unchecked")
    private static <T> Set<T> extract(TransferFunction function, Class<T> type) {
        Set<T> extracts = new HashSet<>();
        Set<Transition> transitions = function.getTransitions();

        for (Transition transition : transitions) {
            try {
                State oldState = transition.getOldState();
                State newState = transition.getNewState();

                if (oldState != null) {
                    extracts.add((T) oldState);
                }

                if (newState != null) {
                    extracts.add((T) newState);
                }
            }
            catch (ClassCastException cce1) {
                try {
                    Input input = transition.getInput();

                    if (input != null) {
                        extracts.add((T) input);
                    }
                }
                catch (ClassCastException cce2) {
                    throw new IllegalArgumentException("Illegal type parameter.");
                }
            }
        }

        return extracts;
    }

    private String formatCollection(Collection<?> collection, String delimiter) {
        String format = "";
        for (Object entry : collection) {
            format = format + entry + delimiter;
        }

        int lastDelimiterIndex = format.lastIndexOf(delimiter);
        if (lastDelimiterIndex > 0) {
            format = format.substring(0, lastDelimiterIndex);
        }

        return format;
    }

    @Override
    public void apply(Input input) {
        currentStates = getTransferFunction().getNewStates(getCurrentStates(), input);
        lastInput = input;
    }
}
