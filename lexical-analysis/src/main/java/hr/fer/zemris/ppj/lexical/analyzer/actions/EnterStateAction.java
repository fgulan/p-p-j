package hr.fer.zemris.ppj.lexical.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analyzer.LexicalAnalyzer;

/**
 * <code>EnterStateAction</code> is a lexical analyzer action which changes the current state of the lexical analyzer.
 *
 * @author Filip Gulan
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class EnterStateAction implements LexerAction {

    /**
     * Action name.
     */
    public static final String ACTION_STRING = "UDJI_U_STANJE";

    private final String newState;

    private final int PRIORITY = 3;

    /**
     * Class constructor, specified the new state of the lexical analyzer.
     *
     * @param newState
     *            the new state.
     * @since 1.0
     */
    public EnterStateAction(final String newState) {
        super();
        this.newState = newState;
    }

    @Override
    public void execute(final LexicalAnalyzer lexer) {
        lexer.setCurrentStateFromName(newState);
    }

    @Override
    public String toString() {
        return ACTION_STRING + " " + newState;
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
