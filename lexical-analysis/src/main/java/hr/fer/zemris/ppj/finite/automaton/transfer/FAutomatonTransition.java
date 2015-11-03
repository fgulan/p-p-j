package hr.fer.zemris.ppj.finite.automaton.transfer;

import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Transition;

public abstract class FAutomatonTransition implements Transition {

    private final State oldState;
    private final State newState;
    private final Input input;

    public FAutomatonTransition(final State oldState, final State newState, final Input input) {
        super();
        this.oldState = oldState;
        this.newState = newState;
        this.input = input;
    }

    @Override
    public State getOldState() {
        return oldState;
    }

    @Override
    public State getNewState() {
        return newState;
    }

    @Override
    public Input getInput() {
        return input;
    }

    public abstract boolean isEpsilonTransition();

    @Override
    public String toString() {
        final String escapedOldState = oldState == null ? "null" : escape(oldState.toString());
        final String escapedInput = input == null ? "null" : escape(input.toString());
        final String escapedNewState = newState == null ? "null" : escape(newState.toString());
        return escapedOldState + " " + escapedInput + " " + escapedNewState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((input == null) ? 0 : input.hashCode());
        result = (prime * result) + ((newState == null) ? 0 : newState.hashCode());
        result = (prime * result) + ((oldState == null) ? 0 : oldState.hashCode());
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FAutomatonTransition other = (FAutomatonTransition) obj;
        if (input == null) {
            if (other.input != null) {
                return false;
            }
        }
        else if (!input.equals(other.input)) {
            return false;
        }
        if (newState == null) {
            if (other.newState != null) {
                return false;
            }
        }
        else if (!newState.equals(other.newState)) {
            return false;
        }
        if (oldState == null) {
            if (other.oldState != null) {
                return false;
            }
        }
        else if (!oldState.equals(other.oldState)) {
            return false;
        }
        return true;
    }

    private static String escape(final String entryString) {
        final String escapedString =
                entryString.replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t").replaceAll(" ", "\\\\_");

        return escapedString;
    }

}