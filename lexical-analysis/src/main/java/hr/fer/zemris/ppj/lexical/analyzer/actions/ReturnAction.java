package hr.fer.zemris.ppj.lexical.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analyzer.LexicalAnalyzer;

public class ReturnAction implements LexerAction {

    public static final String ACTION_STRING = "VRATI_SE";

    private final int offset;

    private final int PRIORITY = 0;

    public ReturnAction(final int offset) {
        this.offset = offset;
    }

    @Override
    public void execute(final LexicalAnalyzer lexer) {
        final int startOffset = (lexer.getStartIndex() + offset) - 1;
        lexer.setFinishIndex(startOffset);
        lexer.setLastIndex(startOffset);
    }

    @Override
    public String toString() {
        return ACTION_STRING + " " + offset;
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
