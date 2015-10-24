package hr.fer.zemris.ppj.lexical.analysis.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.LexicalAnalyzer;

public class ReturnAction implements LexerAction {
    
    private int offset;
    
    public ReturnAction(int offset) {
        this.offset = offset;
    }

    @Override
    public void execute(LexicalAnalyzer lexer) {
        int startOffset = lexer.getStartIndex() + offset;
        lexer.setFinishIndex(startOffset);
        lexer.setLastIndex(startOffset);
    }
}
