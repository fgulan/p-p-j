package hr.fer.zemris.ppj.lexical.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analyzer.LexicalAnalyzer;

public class RejectAction implements LexerAction {

    public static final String ACTION_STRING = "-";

    private final int PRIORITY = 1;

    @Override
    public void execute(final LexicalAnalyzer lexer) {
        lexer.setStartIndex(lexer.getFinishIndex() + 1);
    }

    @Override
    public String toString() {
        return ACTION_STRING;
    }

    @Override
    public int priority() {
        return PRIORITY;
    }

    @Override
    public int compareTo(LexerAction o) {
        return Integer.compare(priority(), o.priority());
    }
}
