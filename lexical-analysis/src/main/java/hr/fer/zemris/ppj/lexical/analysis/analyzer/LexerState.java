package hr.fer.zemris.ppj.lexical.analysis.analyzer;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;


public class LexerState {
    
    private String name;
    private List<LexerRule> rules;
    
    public LexerState(String name) {
        this(name, new LinkedList<LexerRule>());
    }

    public LexerState(String name, List<LexerRule> rules) {
        super();
        this.name = name;
        this.rules = rules;
    }

    public void addRule(LexerRule rule) {
        if (rule == null) {
            throw new RuntimeException("Rule cannot be null|");
        }
        rules.add(rule);
    }
    
    public void apply(Input input) {
        for (LexerRule rule : rules) {
            rule.apply(input);
        }
    }
    
    public String getName() {
        return name;
    }

    public List<LexerRule> getRules() {
        return rules;
    }
}
