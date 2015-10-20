import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.AnalyzerDefinition;
import hr.fer.zemris.ppj.lexical.analysis.analyzer.rules.RuleDefinition;
import hr.fer.zemris.ppj.lexical.analysis.text.manipulation.RegularExpressionManipulator;

/**
 * <code>GLA</code> class is required by the evaluator, to contain a entry point for the lexical analyzer generator.
 *
 * @author Jan Kelemen
 *
 * @version 1.0.0
 */
public class GLA {

    private static final List<String> analyzerStates = new ArrayList<>();
    private static final List<String> lexemeNames = new ArrayList<>();
    private static final List<RuleDefinition> rules = new ArrayList<>();

    /**
     * Entry point for lexical analyzer generator program.
     *
     * @param args
     *            command line arguments aren't used.
     * @since 1.0.0
     */
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            readInputData(reader);
        }
        catch (final IOException e) {
            System.err.println(e.getMessage());
        }

        try (OutputStreamWriter writer = new FileWriter(new File("analizator/definition.txt"))) {
            writeAnalyzerDefinition(writer, new AnalyzerDefinition(analyzerStates, lexemeNames, rules));
        }
        catch (final IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void readInputData(final BufferedReader reader) throws IOException {
        String line = reader.readLine();

        final List<String> regularDefinitions = new ArrayList<>();
        while ((line != null) && line.startsWith("{")) {
            regularDefinitions.add(line);
            line = reader.readLine();
        }

        if ((line != null) && line.startsWith("%X")) {
            for (final String state : line.substring(3).split(" ")) {
                analyzerStates.add(state);
            }
            line = reader.readLine();
        }

        if ((line != null) && line.startsWith("%L")) {
            for (final String name : line.substring(3).split(" ")) {
                lexemeNames.add(name);
            }
            line = reader.readLine();
        }

        final RegularExpressionManipulator manipulator = new RegularExpressionManipulator(regularDefinitions);
        while (line != null) {
            final String analyzerState = line.substring(1, line.indexOf('>'));
            final String regularExpression =
                    manipulator.removeRegularDefinitions(line.substring(line.indexOf('>') + 1));
            final List<String> actions = new ArrayList<>();
            while (!line.startsWith("}")) {
                actions.add(line);
                line = reader.readLine();
            }
            line = reader.readLine();

            rules.add(new RuleDefinition(analyzerState, regularExpression, actions));
        }
    }

    private static void writeAnalyzerDefinition(final OutputStreamWriter writer,
            final AnalyzerDefinition definition) throws IOException {
        writer.write(definition.toString());
    }
}
