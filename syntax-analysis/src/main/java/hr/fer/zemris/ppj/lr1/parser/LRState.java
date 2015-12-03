package hr.fer.zemris.ppj.lr1.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.BasicState;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;

/**
 * <code>LRState</code> represents a finite automaton state used in LR parser creation.
 *
 * @author Filip Gulan
 *
 * @version 1.0
 */
public class LRState extends BasicState {

    private final List<LRItem> items;

    /**
     * Class constructor, specifies the LR items and the id of the state.
     *
     * @param items
     *            the LR items.
     * @param id
     *            the id.
     * @since 1.0
     */
    public LRState(final List<LRItem> items, final String id) {
        super(id);
        this.items = items;
    }

    /**
     * Class constructor, specifies the LR items and the id of the state.
     *
     * @param items
     *            the LR items.
     * @param id
     *            the id.
     * @since 1.0
     */
    public LRState(final List<LRItem> items, final Integer id) {
        this(items, id.toString());
    }

    /**
     * Adds a item to the state.
     *
     * @param item
     *            the item.
     * @since 1.0
     */
    public void addItem(final LRItem item) {
        items.add(item);
    }

    /**
     * Returns items contained in the state.
     *
     * @return -
     * @since 1.0
     */
    public List<LRItem> getItems() {
        return new ArrayList<>(items);
    }

    @Override
    public LRState newInstance(final String id) {
        return new LRState(new ArrayList<LRItem>(), id);
    }

    @Override
    public LRState combine(final Set<State> existing) {
        for (final State state : existing) {
            final LRState lr = (LRState) state;
            items.addAll(lr.getItems());
        }
        return this;
    }

    @Override
    public String toString() {
        String result = getId() + ".";
        final int count = items.size();
        int i = 0;
        for (final LRItem item : items) {
            if (i == (count - 1)) {
                result += item.toString();
            }
            else {
                result += item.toString() + "\n";
            }
            i++;
        }
        return result;
    }
}
