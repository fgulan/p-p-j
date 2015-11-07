package hr.fer.zemris.ppj.lr1.parser;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.grammar.interfaces.Symbol;

public class LRItem {

    private final Production production;
    private int dotIndex;

    public LRItem(Production production, int dotIndex) {
        super();
        this.production = production;
        this.dotIndex = dotIndex;
    }
    
    public static Set<LRItem> fromProduction(Production production) {
        Set<LRItem> result = new HashSet<LRItem>();
        
        for (int i = 0, size = production.rightSide().size(); i <= size; i++) {
            result.add(new LRItem(production, i));
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
        int result = 1;
        result = prime * result + dotIndex;
        result = prime * result + ((production == null) ? 0 : production.hashCode());
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
        LRItem other = (LRItem) obj;
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
        String result = production.leftSide().toString() + ((production.rightSide().isEmpty()) ? " null" : "");
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
