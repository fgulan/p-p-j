package hr.fer.zemris.ppj.lexical.analysis.analyzer.generator;

import java.util.List;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.AnalyzerDefinition;
import hr.fer.zemris.ppj.lexical.analysis.analyzer.LexerRule;

/**
 * <code>AnalyzerGenerator</code> generates a lexical analyzer.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class AnalyzerGenerator {

    /**
     * Generates a analyzer definition from the specified structure.
     *
     * @param analyzerStates
     *            analyzer states.
     * @param lexemeNames
     *            lexeme names.
     * @param rules
     *            analyzer rules.
     * @return generated analyzer definition.
     * @since 1.0
     */
    public static AnalyzerDefinition generateAnalyzerDefinition(final List<String> analyzerStates,
            final List<String> lexemeNames, final List<LexerRule> rules) {
        return new AnalyzerDefinition(analyzerStates, lexemeNames, rules);
    }
}
