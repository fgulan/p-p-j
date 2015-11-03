package hr.fer.zemris.ppj.lexical.analyzer.actions;

import hr.fer.zemris.ppj.lexical.analyzer.LexicalAnalyzer;

public interface LexerAction extends Comparable<LexerAction> {

    void execute(LexicalAnalyzer lexer);

    int priority();
}
