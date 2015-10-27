package hr.fer.zemris.ppj.lexical.analysis.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.LexicalAnalyzer;

public class NewLineAction implements LexerAction {

    @Override
    public void execute(final LexicalAnalyzer lexer) {
        lexer.incrementLineCounter();
    }

}
