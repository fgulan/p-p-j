package hr.fer.zemris.ppj.lr1.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.BasicInput;
import hr.fer.zemris.ppj.finite.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.transfer.ENFAutomatonTransferFunction;
import hr.fer.zemris.ppj.finite.automaton.transfer.EpsilonTransition;
import hr.fer.zemris.ppj.finite.automaton.transfer.FAutomatonTransition;
import hr.fer.zemris.ppj.finite.automaton.transfer.NormalTransition;
import hr.fer.zemris.ppj.grammar.Grammar;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.grammar.interfaces.Symbol;
import hr.fer.zemris.ppj.grammar.symbols.TerminalSymbol;

public class ParserBuilder {
    public final static TerminalSymbol END_SYMBOL = new TerminalSymbol("#");
    
    public static ENFAutomaton fromLR1Grammar(Grammar grammar) {
        Set<Input> alphabet = new HashSet<>();
        Set<FAutomatonTransition> transitions = new HashSet<>();
        Set<State> automatonStates = new HashSet<>();

        List<LRItem> items = new ArrayList<LRItem>();
        Map<LRItem, LRState> states = new HashMap<>();

        for (Production production : grammar.productions()) {
            for (LRItem item : LRItem.fromProduction(production)) {
                items.add(item);
            }
        }
        LRState startState = createStartState(grammar);
        states.put(startState.getItems().get(0), startState);
        automatonStates.add(startState);

        // Generate alphabet symbols
        for (Symbol symbol : grammar.terminalSymbols()) {
            alphabet.add(new BasicInput(symbol));
        }
        for (Symbol symbol : grammar.nonterminalSymbols()) {
            alphabet.add(new BasicInput(symbol));
        }

        Set<LRItem> finishedItems = new HashSet<>();
        int stateIndex = 1;
        boolean changed = true;
        while (changed) {
            changed = false;
            Map<LRItem, LRState> addedStates = new HashMap<>();

            for (Entry<LRItem, LRState> entry : states.entrySet()) {
                LRState state = entry.getValue();
                LRItem item = entry.getKey();
                if (finishedItems.contains(item)) {
                    continue;
                }

                Symbol currentSymbol = null;
                final int dotIndex = item.getDotIndex();
                if (dotIndex < item.getProduction().rightSide().size()) {
                    currentSymbol = item.getProduction().rightSide().get(dotIndex);
                }

                LRItem nextItem = getItemWithNextDot(item, items);
                if (nextItem != null) {
                    Symbol symbol = item.getProduction().rightSide().get(dotIndex);
                    LRItem newItem =
                            new LRItem(nextItem.getProduction(), nextItem.getDotIndex(), item.getTerminalSymbols());
                    LRState nextState = states.get(newItem);
                    LRState addedState = addedStates.get(newItem);

                    if (nextState == null && addedState == null) {
                        nextState = new LRState(new ArrayList<LRItem>(Arrays.asList(newItem)), stateIndex++);
                        automatonStates.add(nextState);
                        addedStates.put(newItem, nextState);
                        changed = true;
                    }
                    else if (nextState == null) {
                        nextState = addedState;
                    }
                    NormalTransition transition = new NormalTransition(state, nextState, new BasicInput(symbol));
                    transitions.add(transition);
                }

                if ((currentSymbol != null) && !currentSymbol.isTerminal()) {
                    int size = item.getProduction().rightSide().size();
                    List<Symbol> leftSymbols = new ArrayList<>();
                    for (int i = dotIndex + 1; i < size; i++) {
                        leftSymbols.add(item.getProduction().rightSide().get(i));
                    }
                    boolean emptySequence = grammar.isEmptySequence(leftSymbols);
                    Set<Symbol> startsWith = grammar.startsWith(leftSymbols);
                    if (emptySequence) {
                        startsWith.addAll(item.getTerminalSymbols());
                    }

                    List<LRItem> newItems = getStartItems(currentSymbol, items);
                    for (LRItem currItem : newItems) {
                        LRItem newItem = new LRItem(currItem.getProduction(), currItem.getDotIndex(), startsWith);

                        LRState nextState = states.get(newItem);
                        LRState addedState = addedStates.get(newItem);
                        if (nextState == null && addedState == null) {
                            nextState = new LRState(new ArrayList<LRItem>(Arrays.asList(newItem)), stateIndex++);
                            addedStates.put(newItem, nextState);
                            automatonStates.add(nextState);
                            changed = true;
                        } else if (nextState == null) {
                            nextState = addedState;
                        }
                        EpsilonTransition transition = new EpsilonTransition(state, nextState);
                        transitions.add(transition);
                    }
                    if (changed) {
                        break;
                    }
                }
                finishedItems.add(item);
            }
            states.putAll(addedStates);
        }
        ENFAutomatonTransferFunction transferFunction = new ENFAutomatonTransferFunction(transitions);
        return new ENFAutomaton(automatonStates, automatonStates, alphabet, transferFunction, startState);
    }

    private static LRState createStartState(Grammar grammar) {
        Production production = grammar.getStartProduction();
        HashSet<Symbol> terminalSymbols = new HashSet<>();
        terminalSymbols.add(END_SYMBOL);
        LRItem startItem = new LRItem(production, 0, terminalSymbols);
        return new LRState(Arrays.asList(new LRItem[] { startItem }), 0);
    }

    private static List<LRItem> getStartItems(Symbol symbol, List<LRItem> items) {
        List<LRItem> newItems = new ArrayList<>();
        for (LRItem item : items) {
            if (item.getProduction().leftSide().equals(symbol) && (item.getDotIndex() == 0)) {
                newItems.add(item);
            }
        }
        return newItems;
    }

    private static LRItem getItemWithNextDot(LRItem item, List<LRItem> items) {
        for (LRItem tempItem : items) {
            if ((tempItem.getDotIndex() == (item.getDotIndex() + 1))
                    && tempItem.getProduction().equals(item.getProduction())) {
                return tempItem;
            }
        }
        return null;
    }
}
