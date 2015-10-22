package hr.fer.zemris.ppj.lexical.analysis.analyzer;

import java.util.Map;

public class LexicalAnalyzer {

    private Map<String, LexerState> states;
    private LexerState currentState;
    private String source;
    
    public LexicalAnalyzer(Map<String, LexerState> states, LexerState currentState, String source) {
        super();
        this.states = states;
        this.currentState = currentState;
        this.source = source;
    }
    
    public void analyze() {
     //TODO analajz   
    }
    
}
