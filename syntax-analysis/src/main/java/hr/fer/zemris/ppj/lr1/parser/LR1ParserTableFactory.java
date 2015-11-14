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
    public static LR1ParserTable fromDFA(DFAutomaton dfa, Symbol startSymbol) {
        LR1ParserTableBuilder builder = new LR1ParserTableBuilder();

        Set<State> states = dfa.getStates();
        int br = 0;
        for (State state : states) {
            // System.out.println(br++ + " " + System.currentTimeMillis());
            LRState lrState;
            try {
                lrState = (LRState) state;
            }
            catch (ClassCastException cce) {
                throw new IllegalArgumentException("Can't create LR parser table from this automaton!");
            }

            Map<Input, Transition> transitions = new HashMap<>();
            for (Transition transition : dfa.getTransferFunction().getTransitions(state, null, null)) {
                transitions.put(transition.getInput(), transition);
            }
            List<LRItem> completeItems = new ArrayList<>();
            List<LRItem> nonCompleteItems = new ArrayList<>();
            separate(lrState, completeItems, nonCompleteItems);

            // completeItems.sort(null);
            for (int i = completeItems.size() - 1; i >= 0; i--) {
                fillTableReduce(state, builder, completeItems.get(i), startSymbol);
            }

            for (LRItem item : nonCompleteItems) {
                fillTableShiftPut(state, dfa, builder, item, transitions);
            }

        }

        return builder.build();
    }

    private static void fillTableShiftPut(State state, DFAutomaton dfa, LR1ParserTableBuilder builder, LRItem lrItem,
            Map<Input, Transition> transitions) {
        Symbol nextSymbol = lrItem.getProduction().rightSide().get(lrItem.getDotIndex());
        Transition transition = transitions.get(new BasicInput(nextSymbol.toString()));
        if (transition != null) {
            TablePair pair = new TablePair(state.getId(), nextSymbol);
            if (nextSymbol.isTerminal()) {
                ParserAction oldAction = builder.getAction(pair);
                ParserAction newAction = new ShiftAction(transition.getNewState().getId());
                if (oldAction != null) {
                    // System.err.println("Resolved shift/reduce conflict for state: " + state.getId() + " symbol: "
                    // + nextSymbol + ". Old action: " + oldAction + " New action:" + newAction);
                }
                builder.addAction(pair, newAction);
            }
            else {
                builder.addAction(pair, new PutAction(transition.getNewState().getId()));
            }
        }

    }

    private static void fillTableReduce(State state, LR1ParserTableBuilder builder, LRItem lrItem, Symbol startSymbol) {

        Symbol leftSide = lrItem.getProduction().leftSide();
        if (leftSide.equals(startSymbol)) {
            builder.addAction(new TablePair(state.getId(), new ArrayList<>(lrItem.getTerminalSymbols()).get(0)),
                    new AcceptAction());
            return;
        }

        for (Symbol termSymbol : lrItem.getTerminalSymbols()) {
            TablePair pair = new TablePair(state.getId(), termSymbol);
            ParserAction oldAction = builder.getAction(pair);
            ReduceAction newAction = new ReduceAction(lrItem.getProduction());
            if ((oldAction != null) && (oldAction instanceof ReduceAction)) {
                if (newAction.production().compareTo(((ReduceAction) oldAction).production()) > 0) {
                    continue;
                }
                // System.err.println("Resolved reduce/reduce conflict for state: " + state.getId() + " symbol: "
                // + termSymbol + ". Old action: " + oldAction + " New action:" + newAction);
            }
            else {
                if (oldAction instanceof ShiftAction) {
                    continue;
                }
            }

            builder.addAction(pair, newAction);
        }
    }

    private static void separate(LRState lrState, List<LRItem> completeItems, List<LRItem> nonCompleteItems) {
        for (LRItem item : lrState.getItems()) {
            Production production = item.getProduction();
            if (production.isEpsilonProduction() || (item.getDotIndex() == production.rightSide().size())) {
                completeItems.add(item);
            }
            else {
                nonCompleteItems.add(item);
            }
        }
    }
}
