package hr.fer.zemris.ppj.lexical.analysis.analyzer;

import java.util.LinkedList;
import java.util.List;

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
    
    public void apply(char input) {
        for (LexerRule rule : rules) {
            rule.apply(input);;
        }
    }
    
    public LexerRule getActiveRule() {
        for (LexerRule rule : rules) {
            if (rule.isAccepting()) {
                return rule;
            }
        }
        return null;
    }
    
    public boolean isAlive() {
        for (LexerRule rule : rules) {
            if (rule.isAlive()) {
                return true;
            }
        }
        return false;
    }
    
    public String getName() {
        return name;
    }

    public List<LexerRule> getRules() {
        return rules;
    }
    
    public void resetAutomatons() {
        for (LexerRule rule : rules) {
            rule.resetAutomaton();
        }
    }
}
