package hr.fer.zemris.ppj.finite.automaton;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.ppj.finite.automaton.interfaces.Automaton;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.interfaces.TransferFunction;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.finite.automaton.transfer.FAutomatonTransition;

public abstract class AbstractAutomaton implements Automaton {

    private final Set<State> states;
    private final Set<State> acceptStates;
    private Set<State> currentStates = new HashSet<>();
    private final Set<Input> inputs;

    private final TransferFunction transferFunction;
    private final State startState;
    private Input lastInput;

    public AbstractAutomaton(final Set<State> states, final Set<State> acceptStates, final Set<Input> inputs,
            final TransferFunction transferFunction, final State startState) {
        super();
        this.states = states;
        this.acceptStates = acceptStates;
        this.inputs = inputs;
        this.transferFunction = transferFunction;
        this.startState = startState;

        currentStates.add(startState);
        currentStates = transferFunction.getNewStates(currentStates, null);
    }

    public AbstractAutomaton(final Set<State> acceptStates, final TransferFunction transferFunction,
            final State startState) {
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
    public boolean hasState(final State state) {
        return states.contains(state);
    }

    @Override
    public boolean isAcceptState(final State state) {
        return acceptStates.contains(state);
    }

    @Override
    public boolean isCurrentState(final State state) {
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
        final Set<State> intersection = getCurrentStates();
        intersection.retainAll(acceptStates);

        return !intersection.isEmpty();
    }

    @Override
    public String toString() {
        final Set<State> states = new TreeSet<>(this.states);
        final Set<Input> inputs = new HashSet<>(this.inputs);
        final Set<State> acceptStates = new TreeSet<>(this.acceptStates);
        final Set<Transition> transitions = new HashSet<>(transferFunction.getTransitions());

        final String result =
                formatCollection(states, " ") + "\n" + formatCollection(acceptStates, " ") + "\n" + startState.getId()
                        + "\n" + formatCollection(inputs, " ") + "\n" + formatCollection(transitions, "\n");

        return result;
    }

    @SuppressWarnings("unchecked")
    private static <T> Set<T> extract(final TransferFunction function, final Class<T> type) {
        final Set<T> extracts = new HashSet<>();
        final Set<Transition> transitions = function.getTransitions();

        for (final Transition transition : transitions) {
            try {
                final State oldState = transition.getOldState();
                final State newState = transition.getNewState();

                if (oldState != null) {
                    extracts.add((T) oldState);
                }

                if (newState != null) {
                    extracts.add((T) newState);
                }
            }
            catch (final ClassCastException cce1) {
                try {
                    final Input input = transition.getInput();

                    if (input != null) {
                        extracts.add((T) input);
                    }
                }
                catch (final ClassCastException cce2) {
                    throw new IllegalArgumentException("Illegal type parameter.");
                }
            }
        }

        return extracts;
    }

    private String formatCollection(final Collection<?> collection, final String delimiter) {
        String format = "";
        for (Object entry : collection) {
            if (!(entry instanceof FAutomatonTransition)) {
                if (entry != null) {
                    entry = entry.toString().replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t").replaceAll(" ",
                            "\\\\_");
                }
            }
            format = format + entry + delimiter;
        }

        final int lastDelimiterIndex = format.lastIndexOf(delimiter);
        if (lastDelimiterIndex > 0) {
            format = format.substring(0, lastDelimiterIndex);
        }

        return format;
    }

    @Override
    public void apply(final Input input) {
        currentStates = getTransferFunction().getNewStates(getCurrentStates(), input);
        lastInput = input;
    }
}
