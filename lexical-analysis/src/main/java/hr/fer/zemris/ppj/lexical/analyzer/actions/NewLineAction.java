package hr.fer.zemris.ppj.lexical.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analyzer.LexicalAnalyzer;

/**
 * <code>NewLineAction</code> is a lexical analyzer action which changes the current line count of the lexical analyzer.
 *
 * @author Filip Gulan
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class NewLineAction implements LexerAction {

    /**
     * Action name.
     */
    public static final String ACTION_STRING = "NOVI_REDAK";

    private final int PRIORITY = 2;

    @Override
    public void execute(final LexicalAnalyzer lexer) {
        lexer.incrementLineCounter();
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
    public int compareTo(final LexerAction o) {
        return Integer.compare(priority(), o.priority());
    }
}
