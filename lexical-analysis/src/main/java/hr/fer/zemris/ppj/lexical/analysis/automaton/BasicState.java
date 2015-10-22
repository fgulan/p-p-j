package hr.fer.zemris.ppj.lexical.analysis.automaton;

import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;

public class BasicState implements State {

    private String id;

    public BasicState(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(State o) {
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        BasicState other = (BasicState) obj;
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

}
