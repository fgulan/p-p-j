package hr.fer.zemris.ppj.lr1.parser;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.ppj.lr1.parser.LR1ParserTable.TablePair;
import hr.fer.zemris.ppj.lr1.parser.actions.ParserAction;

/**
 * Builder class for the <code>LR1ParserTable</code>. Instances of this class can be re-used to build multiple tables.
 *
 * @author Domagoj
 *
 */
public class LR1ParserTableBuilder {

    private final Map<TablePair, ParserAction> actionTable = new HashMap<>();

    /**
     * Adds the given action to the table for the given pair.
     *
     * @param pair
     *            pair to map the action to
     * @param action
     *            action to add
     */
    public void addAction(final TablePair pair, final ParserAction action) {
        actionTable.put(pair, action);
    }

    /**
     * Gets the action mapped to the given pair.
     *
     * @param pair
     *            the pair the action is mapped to
     * @return the action mapped to the given pair if such exists, null otherwise
     */
    public ParserAction getAction(final TablePair pair) {
        return actionTable.get(pair);
    }

    /**
     * Checks if there are any actions mapped to the given pair in the table.
     *
     * @param pair
     *            pair to check
     * @return true if table contains actions mappe dto the given pair, false otherwise
     */
    public boolean containsPair(final TablePair pair) {
        return getAction(pair) != null;
    }

    /**
     * Builds the table. Calling this method will also reset the builder so it can be re-used to build multiple tables.
     *
     * @return the table.
     */
    public LR1ParserTable build() {
        final LR1ParserTable result = new LR1ParserTable(actionTable);
        reset();
        return result;
    }

    /**
     * Resets the builder. When the builder is reset, all pair-action mappings are deleted.
     */
    public void reset() {
        actionTable.clear();
    }
}
