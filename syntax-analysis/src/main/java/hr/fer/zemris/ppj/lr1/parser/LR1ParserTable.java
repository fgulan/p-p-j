package hr.fer.zemris.ppj.lr1.parser;

import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hr.fer.zemris.ppj.grammar.interfaces.Symbol;
import hr.fer.zemris.ppj.lr1.parser.actions.ParserAction;
import hr.fer.zemris.ppj.lr1.parser.actions.RejectAction;

/**
 * <code>LR1ParserTable</code> is a table of actions for the LR(1) parser.
 * 
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class LR1ParserTable {

    /**
     * Represents a state-symbol pair which is mapped to an action
     * in the LR(1) parser table.
     * 
     * @author Domagoj Polancec
     *
     */
    public static class TablePair{
        private String stateId;
        private Symbol symbol;
        
        public TablePair(String stateId, Symbol symbol) {
            super();
            this.stateId = stateId;
            this.symbol = symbol;
        }

        
        public String getState() {
            return stateId;
        }

        
        public Symbol getSymbol() {
            return symbol;
        }


        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((stateId == null) ? 0 : stateId.hashCode());
            result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
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
            TablePair other = (TablePair) obj;
            if (stateId == null) {
                if (other.stateId != null) {
                    return false;
                }
            }
            else if (!stateId.equals(other.stateId)) {
                return false;
            }
            if (symbol == null) {
                if (other.symbol != null) {
                    return false;
                }
            }
            else if (!symbol.equals(other.symbol)) {
                return false;
            }
            return true;
        }
        
    }
    
    private Map<TablePair, ParserAction> actionTable = new HashMap<>();
    
    /**
     * Constructs a new LR(1) parser table from the given action table.
     * @param actionTable LR(1) parser action table as map
     */
    public LR1ParserTable(Map<TablePair, ParserAction> actionTable){
        this.actionTable.putAll(actionTable);
    }
    
    /**
     * Gets the action for the given pair.
     * @param pair pair to get the action for
     * @return action for this pair
     */
    public ParserAction getAction(TablePair pair){
        ParserAction action = actionTable.get(pair);
        return action == null ? new RejectAction() : action;
    }
    
    /**
     * Gets the action for the given state and symbol.
     * @param state state to get the action for
     * @param symbol symbol to get the action for
     * @return action for the given state and symbol
     */
    public ParserAction getAction(LRState state, Symbol symbol){
        return getAction(state.getId(), symbol);
    }
    
    /**
     * Gets the action for the given state id and symbol.
     * @param stateId state id to get the action for
     * @param symbol symbol to get the action for
     * @return action for the given state id and symbol
     */
    public ParserAction getAction(String stateId, Symbol symbol){
        return getAction(new TablePair(stateId, symbol));
    }
    
    @Override
    public String toString() {
        Map<String, List<String>> stateList = new HashMap<>();
        for (TablePair pair: actionTable.keySet()){
            if (stateList.get(pair.stateId) == null){
                stateList.put(pair.stateId, new ArrayList<>());
            }
            
            stateList.get(pair.stateId).add(pair.symbol.toString() + " " + actionTable.get(pair).toString());
        }
        
        String result = "";
        for (Entry<String, List<String>> entry: stateList.entrySet()){
            result += entry.getKey() + "\n" + " " + printList(entry.getValue()) + "\n";
        }
        
        return result;
    }

    private String printList(List<String> value) {
        String result = "";
        int listSize = value.size();
        for (int i = 0; i < listSize; i++){
            result += value.get(i);
            if (i != listSize - 1){
                result += "\n";
            }
        }
        return result;
    }
}
