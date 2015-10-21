package hr.fer.zemris.ppj.lexical.analysis.analyzer;

import java.util.List;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.actions.LexerAction;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Automaton;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;

public class LexerRule {

    private LexerState initialState;
    private Automaton automaton;
    private List<LexerAction> actions;
    
    public LexerRule(LexerState initialState, Automaton automaton, List<LexerAction> actions) {
        super();
        this.initialState = initialState;
        this.automaton = automaton;
        this.actions = actions;
    }

    public void apply(Input input) {
        automaton.apply(input);
    }
    
    public void execute(LexicalAnalyzer lexer) {
        for (LexerAction action : actions) {
            action.execute(lexer);
        }
    }
    public LexerState getInitialState() {
        return initialState;
    }

    public Automaton getAutomaton() {
        return automaton;
    }

    public List<LexerAction> getActions() {
        return actions;
    }
}
