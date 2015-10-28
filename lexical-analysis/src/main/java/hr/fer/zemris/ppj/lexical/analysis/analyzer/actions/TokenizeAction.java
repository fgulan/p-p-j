package hr.fer.zemris.ppj.lexical.analysis.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.LexicalAnalyzer;

public class TokenizeAction implements LexerAction {

    private final String token;

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
}
