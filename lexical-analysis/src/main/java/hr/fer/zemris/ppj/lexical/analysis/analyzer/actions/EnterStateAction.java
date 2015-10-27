package hr.fer.zemris.ppj.lexical.analysis.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.LexicalAnalyzer;

public class EnterStateAction implements LexerAction {

    private final String newState;

    public EnterStateAction(final String newState) {
        super();
        this.newState = newState;
    }

    @Override
    public void execute(final LexicalAnalyzer lexer) {
        lexer.setCurrentStateFromName(newState);
    }

}
