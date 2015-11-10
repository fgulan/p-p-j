package hr.fer.zemris.ppj.finite.automaton.interfaces;

import java.util.Set;

/**
 * Represents an automaton state.
 *
 * @author Domagoj Polancec
 * @version 1.0*
 */
public interface State extends Comparable<State> {

    /**
     * Gets the state id.
     *
     * @return the state id
     */
    String getId();

    /**
     * Creates a new instance of the state with the specified ID.
     *
     * @param id
     *            the ID.
     * @return created state.
     */
    State newInstance(String id);

    /**
     * Combines the given set of statetes to a new state.
     *
     * @param existing
     *            existing set of states that need to be combined.
     * @return the combined state.
     */
    State combine(Set<State> existing); // This method should only be used in combination with method newInstance() as
                                        // it has destructive properties on the state.
}
