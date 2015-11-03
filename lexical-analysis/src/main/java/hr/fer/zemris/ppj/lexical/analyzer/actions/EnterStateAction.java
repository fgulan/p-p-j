package hr.fer.zemris.ppj.lexical.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analyzer.LexicalAnalyzer;

public class EnterStateAction implements LexerAction {

    public static final String ACTION_STRING = "UDJI_U_STANJE";

    private final String newState;

    private final int PRIORITY = 3;

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
    public int compareTo(LexerAction o) {
        return Integer.compare(priority(), o.priority());
    }
}