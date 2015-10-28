package hr.fer.zemris.ppj.lexical.analysis.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.LexicalAnalyzer;

public class RejectAction implements LexerAction {

    public static final String ACTION_STRING = "-";

    @Override
    public void execute(final LexicalAnalyzer lexer) {
        lexer.setStartIndex(lexer.getFinishIndex() + 1);
    }

    @Override
    public String toString() {
        return ACTION_STRING;
    }
}
