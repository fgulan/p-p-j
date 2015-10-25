package hr.fer.zemris.ppj.lexical.analysis.analyzer.rules;

import java.util.List;

import hr.fer.zemris.ppj.lexical.analysis.automaton.generator.ENFAutomatonGenerator;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Automaton;

/**
 * <code>RuleDefinition</code>
 *
 * @author Jan Kelemen
 *
 * @version
 */
public class RuleDefinition {

    private final String analyzerState;
    private final Automaton automaton;
    private final List<String> actions;

    /**
     * Class constructor, specifies the associated analyzer state, regular expression and actions.
     *
     * @param analyzerState
     *            the analyzer state.
     * @param regularExpression
     *            the regulare expression.
     * @param actions
     *            the actions.
     * @since 1.0
     */
    public RuleDefinition(final String analyzerState, final String regularExpression, final List<String> actions) {
        this.analyzerState = analyzerState;
        // automaton = Automatons.minimize(new ENFAutomatonGenerator().fromRegularExpression(regularExpression));
        automaton = new ENFAutomatonGenerator().fromRegularExpression(regularExpression);
        this.actions = actions;
    }

    /**
     * {@inheritDoc} <br>
     *
     * Format: automaton <br>
     * <br>
     * analyzerState <br>
     * { <br>
     * actions... <br>
     * }
     *
     * @see java.lang.Object#toString()
     * @since 1.0
     */
    @Override
    public String toString() {
        String result = "";
        result += automaton.toString() + "\n\n";
        result += analyzerState + "\n";
        result += "{\n";
        for (final String action : actions) {
            result += action + "\n";
        }
        result += "}";

        return result;
    }
}
