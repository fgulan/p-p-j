package hr.fer.zemris.ppj.lr1.parser;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.BasicState;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.grammar.interfaces.Symbol;

public class LRState extends BasicState {

    private final Production production;
    private int dotIndex;

    public LRState(Production production, final int dotIndex, String id) {
        super(id);
        if (dotIndex > production.rightSide().size() || dotIndex < 0) {
            throw new IllegalArgumentException("Dot index is out of bounds");
        }
        this.dotIndex = dotIndex;
        this.production = production;
    }

    public LRState(Production production, final int dotIndex, Integer id) {
        this(production, dotIndex, id.toString());
    }
    
    public static Set<LRState> fromProduction(Production production, int startNameIndex) {
        Set<LRState> result = new HashSet<LRState>();

        for (int i = 0, size = production.rightSide().size(); i <= size; i++) {
            result.add(new LRState(production, i, startNameIndex++));
        }

        return result;
    }

    public int getDotIndex() {
        return dotIndex;
    }

    public void setDotIndex(int dotIndex) {
        this.dotIndex = dotIndex;
    }

    public Production getProduction() {
        return production;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + dotIndex;
        result = prime * result + ((production == null) ? 0 : production.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        LRState other = (LRState) obj;
        if (dotIndex != other.dotIndex) {
            return false;
        }
        if (production == null) {
            if (other.production != null) {
                return false;
            }
        } else if (!production.equals(other.production)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String result = this.getId() + ". " + production.leftSide().toString()
                + ((production.rightSide().isEmpty()) ? " null" : "");
        
        for (int i = 0, size = production.rightSide().size(); i < size; i++) {
            Symbol symbol = production.rightSide().get(i);
            if (i == dotIndex) {
                result += " *" + symbol.toString();
            } else {
                result += " " + symbol.toString();
            }
        }
        return result;
    }

}
