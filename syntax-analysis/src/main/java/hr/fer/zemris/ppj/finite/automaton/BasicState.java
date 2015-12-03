package hr.fer.zemris.ppj.finite.automaton;

import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.interfaces.State;

/**
 * <code>BasicState</code> represents a basic state of the automaton.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class BasicState implements State {

    private final String id;

    /**
     * Class constructor, specifies the id of the state.
     *
     * @param id
     *            the id.
     * @since 1.0
     */
    public BasicState(final String id) {
        this.id = id;
    }

    @Override
    public int compareTo(final State o) {
        return id.compareTo(o.getId());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
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
        final BasicState other = (BasicState) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        }
        else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public BasicState newInstance(final String id) {
        return new BasicState(id);
    }

    @Override
    public BasicState combine(final Set<State> existing) {
        return this;
    }

}
