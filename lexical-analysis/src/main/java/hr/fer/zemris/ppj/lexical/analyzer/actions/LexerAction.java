package hr.fer.zemris.ppj.lexical.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analyzer.LexicalAnalyzer;

/**
 * <code>LexerAction</code> is a interface for lexical analyzer actions.
 *
 * @author Filip Gulan
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public interface LexerAction extends Comparable<LexerAction> {

    /**
     * Executes the action on the given lexical analyzer.
     *
     * @param lexer
     *            the lexical analyzer.
     * @since 1.0
     */
    void execute(LexicalAnalyzer lexer);

    /**
     * Returns the priority of the action.
     *
     * @return the priority.
     * @since 1.0
     */
    int priority();
}
