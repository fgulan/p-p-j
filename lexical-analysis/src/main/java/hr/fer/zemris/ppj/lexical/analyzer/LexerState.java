package hr.fer.zemris.ppj.lexical.analyzer;

import java.util.ArrayList;
import java.util.List;

public class LexerState {

    private final String name;
    private final List<LexerRule> rules;

    public LexerState(final String name) {
        this(name, new ArrayList<LexerRule>());
    }

    public LexerState(final String name, final List<LexerRule> rules) {
        super();
        this.name = name;
        this.rules = rules;
    }

    public void addRule(final LexerRule rule) {
        if (rule == null) {
            throw new RuntimeException("Rule cannot be null|");
        }
        rules.add(rule);
    }

    public void apply(final char input) {
        for (final LexerRule rule : rules) {
            rule.apply(input);
            ;
        }
    }

    public void apply(final String input) {
        for (final LexerRule rule : rules) {
            rule.apply(input);
            ;
        }
    }

    public LexerRule getActiveRule() {
        for (final LexerRule rule : rules) {
            if (rule.isAccepting()) {
                return rule;
            }
        }
        return null;
    }

    public boolean isAlive() {
        for (final LexerRule rule : rules) {
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
        for (final LexerRule rule : rules) {
            rule.resetAutomaton();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LexerState other = (LexerState) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
