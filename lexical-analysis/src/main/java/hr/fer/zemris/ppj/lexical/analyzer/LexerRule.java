package hr.fer.zemris.ppj.lexical.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.ppj.finite.automaton.Automatons;
import hr.fer.zemris.ppj.finite.automaton.generator.ENFAutomatonGenerator;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Automaton;
import hr.fer.zemris.ppj.lexical.analyzer.actions.LexerAction;

/**
 * <code>LexerRule</code> is a rule of the lexical analyzer.
 *
 * @author Filip Gulan
 *
 * @version 1.0
 */
public class LexerRule {

    private final String lexerState;
    private final Automaton automaton;
    private final List<LexerAction> actions;

    /**
     * Class constructor, specifies the lexer state, the regular expression and the actions of the rule.
     *
     * @param lexerState
     *            the lexer state.
     * @param regularExpression
     *            the regular expression.
     * @param actions
     *            the actions.
     * @since 1.0
     */
    public LexerRule(final String lexerState, final String regularExpression, final List<LexerAction> actions) {
        super();
        this.lexerState = lexerState;
        automaton = new ENFAutomatonGenerator().fromRegularExpression(regularExpression);
        this.actions = actions;
    }

    /**
     * Class constructor, specifies the lexer state, the regular expression and the actions of the rule.
     *
     * @param lexerState
     *            the lexer state.
     * @param automaton
     *            the regular expression.
     * @param actions
     *            the actions.
     * @since 1.0
     */
    public LexerRule(final String lexerState, final Automaton automaton, final List<LexerAction> actions) {
        super();
        this.lexerState = lexerState;
        this.automaton = automaton;
        this.actions = new ArrayList<LexerAction>(actions);
        Collections.sort(this.actions);
    }

    /**
     * Applies a input to the automaton of the rule.
     *
     * @param input
     *            the input
     * @since 1.0
     */
    public void apply(final char input) {
        Automatons.apply(automaton, input);
    }

    /**
     * Applies a input to the automaton of the rule.
     *
     * @param input
     *            the input
     * @since 1.0
     */
    public void apply(final String input) {
        Automatons.apply(automaton, input);
    }

    /**
     * Executes the actions on the lexical analyzer.
     *
     * @param lexer
     *            the lexical analyzer.
     * @since 1.0
     */
    public void execute(final LexicalAnalyzer lexer) {
        for (final LexerAction action : actions) {
            action.execute(lexer);
        }
    }

    /**
     * Resets the automaton of the rule.
     *
     * @since 1.0
     */
    public void resetAutomaton() {
        automaton.reset();
    }

    /**
     * Checks if the automaton of the rule is in a accepting state.
     *
     * @return <code>true</code> if the automaton is in a accepting state, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isAccepting() {
        return automaton.isAccepting();
    }

    /**
     * Checks if the automaton can accept more input.
     *
     * @return <code>true</code> if the automaton accepts more input, <code>false</code> otherwise.
     * @since
     */
    public boolean isAlive() {
        return automaton.getCurrentStates().size() != 0;
    }

    /**
     * Returns the automaton of the rule.
     *
     * @return the automaton.
     * @since 1.0
     */
    public Automaton getAutomaton() {
        return automaton;
    }

    /**
     * Returns the actions of the rule.
     *
     * @return the actions.
     * @since 1.0
     */
    public List<LexerAction> getActions() {
        return actions;
    }

    @Override
    public String toString() {
        String result = "";
        result += automaton.toString() + "\n\n";
        result += lexerState + "\n";
        result += "{\n";
        for (final LexerAction action : actions) {
            result += action + "\n";
        }
        result += "}";

        return result;
    }
}
