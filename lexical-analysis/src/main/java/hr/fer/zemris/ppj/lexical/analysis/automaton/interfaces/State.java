package hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces;

/**
 * Represents an automaton state.
 *
 * @author Domagoj Polancec
 * @version 1.0
 *
 */
public interface State extends Comparable<State> {

    /**
     * Gets the state id.
     *
     * @return the state id
     */
    String getId();
}
