package hr.fer.zemris.ppj.lexical.analyzer;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>LexerState</code> represents a state of the lexical analyzer.
 *
 * @author Filip Gulan
 *
 * @version 1.0
 */
public class LexerState {

    private final String name;
    private final List<LexerRule> rules;

    /**
     * Class constructor, specifies the name of the state and a empty set of rules.
     *
     * @param name
     *            the name.
     * @since 1.0
     */
    public LexerState(final String name) {
        this(name, new ArrayList<LexerRule>());
    }

    /**
     * Class constructor, specifes the name and the rules of the state.
     *
     * @param name
     *            the name.
     * @param rules
     *            the rules.
     * @since 1.0
     */
    public LexerState(final String name, final List<LexerRule> rules) {
        super();
        this.name = name;
        this.rules = rules;
    }

    /**
     * Adds a rule to the state.
     *
     * @param rule
     *            the rule.
     * @since 1.0
     */
    public void addRule(final LexerRule rule) {
        if (rule == null) {
            throw new RuntimeException("Rule cannot be null|");
        }
        rules.add(rule);
    }

    /**
     * Applies the input to all rules in the state.
     *
     * @param input
     *            the input.
     * @since 1.0
     */
    public void apply(final char input) {
        for (final LexerRule rule : rules) {
            rule.apply(input);
        }
    }

    /**
     * Applies the input to all rules in the state.
     *
     * @param input
     *            the input.
     * @since 1.0
     */
    public void apply(final String input) {
        for (final LexerRule rule : rules) {
            rule.apply(input);
        }
    }

    /**
     * Returns the first active rule of the state.
     *
     * @return active rule of the state or <code>null</code> if no rules are active.
     * @since 1.0
     */
    public LexerRule getActiveRule() {
        for (final LexerRule rule : rules) {
            if (rule.isAccepting()) {
                return rule;
            }
        }
        return null;
    }

    /**
     * Checks if the state contains live rules.
     *
     * @return <code>true</code> if there are active rules, <code>false</code>otherwise.
     * @since 1.0
     */
    public boolean isAlive() {
        for (final LexerRule rule : rules) {
            if (rule.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the name of the state.
     *
     * @return the name.
     * @since 1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the rules of the state.
     *
     * @return the rules.
     * @since 1.0
     */
    public List<LexerRule> getRules() {
        return rules;
    }

    /**
     * Resets the automatons of the associated rules.
     *
     * @since 1.0
     */
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
