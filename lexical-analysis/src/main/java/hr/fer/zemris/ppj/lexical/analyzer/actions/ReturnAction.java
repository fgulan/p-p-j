package hr.fer.zemris.ppj.lexical.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analyzer.LexicalAnalyzer;

/**
 * <code>ReturnAction</code> is a lexical analyzer action which changes the point at which the matched lexeme ends.
 *
 * @author Filip Gulan
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class ReturnAction implements LexerAction {

    /**
     * Action name.
     */
    public static final String ACTION_STRING = "VRATI_SE";

    private final int offset;

    private final int PRIORITY = 0;

    /**
     * Class constructor, specifies the offset from the beggining of the lexeme.
     *
     * @param offset
     *            the offset.
     * @since 1.0
     */
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
    public int compareTo(final LexerAction o) {
        return Integer.compare(priority(), o.priority());
    }
}
