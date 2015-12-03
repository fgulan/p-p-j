package hr.fer.zemris.ppj.lr1.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.BasicInput;
import hr.fer.zemris.ppj.finite.automaton.DFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.grammar.interfaces.Symbol;
import hr.fer.zemris.ppj.lr1.parser.LR1ParserTable.TablePair;
import hr.fer.zemris.ppj.lr1.parser.actions.AcceptAction;
import hr.fer.zemris.ppj.lr1.parser.actions.ParserAction;
import hr.fer.zemris.ppj.lr1.parser.actions.PutAction;
import hr.fer.zemris.ppj.lr1.parser.actions.ReduceAction;
import hr.fer.zemris.ppj.lr1.parser.actions.ShiftAction;

/**
 * Factory class that creates LR(1) parser tables.
 *
 * @author Domagoj Polancec
 *
 */
public class LR1ParserTableFactory {

    /**
     * Creates an LR(1) parser table from the given Deterministic Finite Automaton and the given grammar start symbol.
     *
     * @param dfa
     *            automaton to generate the table from
     * @param startSymbol
     *            grammar start symbol, used to determine which state and symbol to call AcceptAction for
     * @return the generated parser table
     */
    public static LR1ParserTable fromDFA(final DFAutomaton dfa, final Symbol startSymbol) {
        final LR1ParserTableBuilder builder = new LR1ParserTableBuilder();

        final Set<State> states = dfa.getStates();
        for (final State state : states) {
            LRState lrState;
            try {
                lrState = (LRState) state;
            }
            catch (final ClassCastException cce) {
                throw new IllegalArgumentException("Can't create LR parser table from this automaton!");
            }

            final Map<Input, Transition> transitions = new HashMap<>();
            for (final Transition transition : dfa.getTransferFunction().getTransitions(state, null, null)) {
                transitions.put(transition.getInput(), transition);
            }
            final List<LRItem> completeItems = new ArrayList<>();
            final List<LRItem> nonCompleteItems = new ArrayList<>();
            separate(lrState, completeItems, nonCompleteItems);

            // completeItems.sort(null);
            for (int i = completeItems.size() - 1; i >= 0; i--) {
                fillTableReduce(state, builder, completeItems.get(i), startSymbol);
            }

            for (final LRItem item : nonCompleteItems) {
                fillTableShiftPut(state, dfa, builder, item, transitions);
            }

        }

        return builder.build();
    }

    private static void fillTableShiftPut(final State state, final DFAutomaton dfa, final LR1ParserTableBuilder builder,
            final LRItem lrItem, final Map<Input, Transition> transitions) {
        final Symbol nextSymbol = lrItem.getProduction().rightSide().get(lrItem.getDotIndex());
        final Transition transition = transitions.get(new BasicInput(nextSymbol.toString()));
        if (transition != null) {
            final TablePair pair = new TablePair(state.getId(), nextSymbol);
            if (nextSymbol.isTerminal()) {
                final ParserAction oldAction = builder.getAction(pair);
                final ParserAction newAction = new ShiftAction(transition.getNewState().getId());
                if ((oldAction != null) && !oldAction.equals(newAction)) {
                    System.err.println("Resolved shift/reduce conflict for state: " + state.getId() + " symbol: "
                            + nextSymbol + ". Old action: " + oldAction + " New action:" + newAction);
                }
                builder.addAction(pair, newAction);
            }
            else {
                builder.addAction(pair, new PutAction(transition.getNewState().getId()));
            }
        }

    }

    private static void fillTableReduce(final State state, final LR1ParserTableBuilder builder, final LRItem lrItem,
            final Symbol startSymbol) {

        final Symbol leftSide = lrItem.getProduction().leftSide();
        if (leftSide.equals(startSymbol)) {
            builder.addAction(new TablePair(state.getId(), new ArrayList<>(lrItem.getTerminalSymbols()).get(0)),
                    new AcceptAction());
            return;
        }

        for (final Symbol termSymbol : lrItem.getTerminalSymbols()) {
            final TablePair pair = new TablePair(state.getId(), termSymbol);
            final ParserAction oldAction = builder.getAction(pair);
            final ReduceAction newAction = new ReduceAction(lrItem.getProduction());
            if ((oldAction != null) && (oldAction instanceof ReduceAction)) {
                if (newAction.production().compareTo(((ReduceAction) oldAction).production()) > 0) {
                    continue;
                }

                if (!oldAction.equals(newAction)) {
                    System.err.println("Resolved reduce/reduce conflict for state: " + state.getId() + " symbol: "
                            + termSymbol + ". Old action: " + oldAction + " New action:" + newAction);
                }
            }
            else {
                if (oldAction instanceof ShiftAction) {
                    continue;
                }
            }

            builder.addAction(pair, newAction);
        }
    }

    private static void separate(final LRState lrState, final List<LRItem> completeItems,
            final List<LRItem> nonCompleteItems) {
        for (final LRItem item : lrState.getItems()) {
            final Production production = item.getProduction();
            if (production.isEpsilonProduction() || (item.getDotIndex() == production.rightSide().size())) {
                completeItems.add(item);
            }
            else {
                nonCompleteItems.add(item);
            }
        }
    }
}
