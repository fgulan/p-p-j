package hr.fer.zemris.ppj.lexical.analysis.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.LexicalAnalyzer;

public class EnterStateAction implements LexerAction {

    private String newState;
    
    public EnterStateAction(String newState) {
        super();
        this.newState = newState;
    }
    
    @Override
    public void execute(LexicalAnalyzer lexer) {
        lexer.setCurrentStateFromName(newState);
    }

}
