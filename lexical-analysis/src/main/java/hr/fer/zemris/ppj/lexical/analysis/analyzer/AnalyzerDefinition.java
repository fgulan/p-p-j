package hr.fer.zemris.ppj.lexical.analysis.analyzer;

import java.util.Collection;
import java.util.List;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.rules.RuleDefinition;

/**
 * <code>AnalyzerDefinition</code> contains a definition of the analyzer.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class AnalyzerDefinition {

    private final List<String> analyzerStates;
    private final List<String> lexemeNames;
    private final List<RuleDefinition> rules;

    /**
     * Class constructor, creates a analyzer definition from the specified structure.
     *
     * @param analyzerStates
     *            analyzer states.
     * @param lexemeNames
     *            lexeme names
     * @param rules
     *            analyzer rules
     * @since 1.0
     */
    public AnalyzerDefinition(final List<String> analyzerStates, final List<String> lexemeNames,
            final List<RuleDefinition> rules) {
        this.analyzerStates = analyzerStates;
        this.lexemeNames = lexemeNames;
        this.rules = rules;
    }

    /**
     * {@inheritDoc}
     *
     * Format: list of analyzer states. <br>
     * <br>
     * list of lexeme names. <br>
     * <br>
     * list of rule definitions... <br>
     *
     * All lists are space delimited.
     *
     * @see hr.fer.zemris.ppj.lexical.analysis.analyzer.rules.RuleDefinition#toString()
     * @see java.lang.Object#toString()
     * @since 1.0
     */
    @Override
    public String toString() {
        String result = "";
        result += collectionToString(analyzerStates) + "\n";
        result += "\n";
        result += collectionToString(lexemeNames) + "\n";
        result += "\n";
        for (final RuleDefinition definition : rules) {
            result += definition.toString() + "\n\n";
        }
        return result;
    }

    private String collectionToString(final Collection<?> list) {
        String result = "";
        for (final Object item : list) {
            result += item + " ";
        }
        return result.substring(0, result.length() - 1);
    }
}
