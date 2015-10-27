package hr.fer.zemris.ppj.lexical.analysis.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.LexicalAnalyzer;

public class TokenizeAction implements LexerAction {
    
    private String token;
    
    public TokenizeAction(String token) {
        super();
        this.token = token;
    }
    
    @Override
    public void execute(LexicalAnalyzer lexer) {
        lexer.getOutput().println(token + " " + lexer.getLineCounter() + " " + lexer.getCurrentPhrase());
        lexer.setStartIndex(lexer.getFinishIndex() + 1);
    }
}
