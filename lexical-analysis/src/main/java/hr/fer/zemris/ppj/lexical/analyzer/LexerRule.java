package hr.fer.zemris.ppj.lexical.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.ppj.finite.automaton.Automatons;
import hr.fer.zemris.ppj.finite.automaton.generator.ENFAutomatonGenerator;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Automaton;
import hr.fer.zemris.ppj.lexical.analyzer.actions.LexerAction;

public class LexerRule {

    private final String lexerState;
    private final Automaton automaton;
    private final List<LexerAction> actions;

    public LexerRule(final String lexerState, final String regularExpression, final List<LexerAction> actions) {
        super();
        this.lexerState = lexerState;
        automaton = new ENFAutomatonGenerator().fromRegularExpression(regularExpression);
        this.actions = actions;
    }

    public LexerRule(final String lexerState, final Automaton automaton, final List<LexerAction> actions) {
        super();
        this.lexerState = lexerState;
        this.automaton = automaton;
        this.actions = new ArrayList<LexerAction>(actions);
        Collections.sort(this.actions);
    }

    public void apply(final char input) {
        Automatons.apply(automaton, input);
    }

    public void apply(final String input) {
        Automatons.apply(automaton, input);
    }

    public void execute(final LexicalAnalyzer lexer) {
        for (final LexerAction action : actions) {
            action.execute(lexer);
        }
    }

    public void resetAutomaton() {
        automaton.reset();
    }

    public boolean isAccepting() {
        return automaton.isAccepting();
    }

    public boolean isAlive() {
        return automaton.getCurrentStates().size() != 0;
    }

    public Automaton getAutomaton() {
        return automaton;
    }

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
