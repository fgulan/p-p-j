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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LexerState other = (LexerState) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
    
}
