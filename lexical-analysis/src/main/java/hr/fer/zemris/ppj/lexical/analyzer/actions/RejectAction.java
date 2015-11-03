package hr.fer.zemris.ppj.lexical.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analyzer.LexicalAnalyzer;

/**
 * <code>RejectAction</code> is a lexical analyzer action which rejects the matched lexeme.
 *
 * @author Filip Gulan
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class RejectAction implements LexerAction {

    /**
     * Action name.
     */
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
    public int compareTo(final LexerAction o) {
        return Integer.compare(priority(), o.priority());
    }
}
