package hr.fer.zemris.ppj.lexical.analysis.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.LexicalAnalyzer;

public class NewLineAction implements LexerAction {

    public static final String ACTION_STRING = "NOVI_REDAK";

    @Override
    public void execute(final LexicalAnalyzer lexer) {
        lexer.incrementLineCounter();
    }

    @Override
    public String toString() {
        return ACTION_STRING;
    }
}
