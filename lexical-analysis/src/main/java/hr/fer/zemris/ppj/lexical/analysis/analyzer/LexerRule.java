package hr.fer.zemris.ppj.lexical.analysis.analyzer;

import java.util.List;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.actions.LexerAction;
import hr.fer.zemris.ppj.lexical.analysis.automaton.Automatons;
import hr.fer.zemris.ppj.lexical.analysis.automaton.generator.ENFAutomatonGenerator;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Automaton;

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
        this.actions = actions;
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
