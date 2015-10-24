package hr.fer.zemris.ppj.lexical.analysis.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.LexicalAnalyzer;

public class NewLineAction implements LexerAction {

    @Override
    public void execute(LexicalAnalyzer lexer) {
        lexer.incrementLineCounter();
    }

}
