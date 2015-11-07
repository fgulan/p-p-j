package hr.fer.zemris.ppj.lr1.parser;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.BasicState;

public class LRState extends BasicState {
    
    private final Set<LRItem> items;
    
    public LRState(Set<LRItem> items, String id) {
        super(id);
        this.items = items;
    }
    
    public void addItem(LRItem item) {
        items.add(item);
    }

    public Set<LRItem> getItems() {
        return new HashSet<>(items);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((items == null) ? 0 : items.hashCode());
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
        if (items == null) {
            if (other.items != null) {
                return false;
            }
        } else if (!items.equals(other.items)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String result = this.getId() + ". ";
        int count = items.size();
        int i = 0;
        for (LRItem item : items) {
            if (i == count - 1) {
                result += item.toString();
            } else {
                result += item.toString() + "\n";
            }
            i++;
        }
        return result;
    }
}
