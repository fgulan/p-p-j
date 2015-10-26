package hr.fer.zemris.ppj.lexical.analysis.automaton;

import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;

public class BasicInput implements Input {

    private Object representation;

    public BasicInput(Object representation) {
        this.representation = representation;
    }

    public Object getRepresentation() {
        return representation;
    }

    @Override
    public String toString() {
        return representation.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((representation == null) ? 0 : representation.hashCode());
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
        BasicInput other = (BasicInput) obj;
        if (representation == null) {
            if (other.representation != null) {
                return false;
            }
        } else if (other.representation == null){
            return false;
        }
        
        if (representation.equals(other.representation)) {
            return true;
        }
        
        if (representation.toString().equals(other.representation.toString())) {
            return true;
        }
        
        return false;
    }

    @Override
    public int compareTo(Input o) {
        return this.toString().compareTo(o.toString());
    }

}
