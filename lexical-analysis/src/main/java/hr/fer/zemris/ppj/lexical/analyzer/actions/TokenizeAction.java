package hr.fer.zemris.ppj.lexical.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analyzer.LexicalAnalyzer;

public class TokenizeAction implements LexerAction {

    private final String token;

    private final int PRIORITY = 1;

    public TokenizeAction(final String token) {
        super();
        this.token = token;
    }

    @Override
    public void execute(final LexicalAnalyzer lexer) {
        lexer.getOutput().println(token + " " + lexer.getLineCounter() + " " + lexer.getCurrentPhrase());
        lexer.setStartIndex(lexer.getFinishIndex() + 1);
    }

    @Override
    public String toString() {
        return token;
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
