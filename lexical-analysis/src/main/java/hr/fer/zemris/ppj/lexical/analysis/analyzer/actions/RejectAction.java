package hr.fer.zemris.ppj.lexical.analysis.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.LexicalAnalyzer;

public class RejectAction implements LexerAction {

    @Override
    public void execute(LexicalAnalyzer lexer) {
        lexer.setStartIndex(lexer.getFinishIndex() + 1);
    }
}
