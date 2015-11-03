import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ppj.finite.automaton.generator.ENFAutomatonGenerator;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Automaton;
import hr.fer.zemris.ppj.lexical.analyzer.LexerRule;
import hr.fer.zemris.ppj.lexical.analyzer.LexerState;
import hr.fer.zemris.ppj.lexical.analyzer.LexicalAnalyzer;
import hr.fer.zemris.ppj.lexical.analyzer.actions.ActionFactory;
import hr.fer.zemris.ppj.lexical.analyzer.actions.LexerAction;

/**
 * <code>LA</code> class is required by the evaluator, to contain a entry point for the lexical analyzer generator.
 *
 * @author Filip Gulan
 * @author Jan Kelemen
 *
 * @version 1.0.0
 */
public class LA {

    private static final Map<String, LexerState> states = new HashMap<>();
    private static LexerState startState;
    private static String source;

    /**
     * Entry point for lexical analyzer generator program.
     *
     * @param args
     *            command line arguments aren't used.
     * @since 1.0.0
     */
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            readSourceCode(reader);
        }
        catch (final Exception e) {
            System.err.println("Unable to read from system input. Message: " + e.getMessage());
        }

        try (BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(new FileInputStream("definition.txt"), StandardCharsets.UTF_8));) {
            readInputData(inputReader);
        }
        catch (final Exception e) {
            System.err.println("Unable to read from file input. Message: " + e.getMessage());
        }

        new LexicalAnalyzer(states, startState).analyze(source, new PrintStream(System.out));
    }

    private static void readSourceCode(final BufferedReader reader) throws IOException {
        String line = reader.readLine();
        final StringBuilder builder = new StringBuilder();
        while (line != null) {
            builder.append(line);
            builder.append("\n");
            line = reader.readLine();
        }
        source = builder.toString();
    }

    private static void readInputData(final BufferedReader reader) throws IOException {
        // Read states
        String line = reader.readLine();

        // Parse states
        for (final String stateString : line.split(" ")) {
            final LexerState state = new LexerState(stateString);
            if (startState == null) {
                startState = state;
            }
            states.put(stateString, state);
        }

        // Read empty line
        line = reader.readLine();

        // Read tokens
        line = reader.readLine();

        // Parse tokens
        // TODO for now - do we need them? No. @Jan

        // Read empty line
        line = reader.readLine();

        while (!line.equals("END")) {
            readRuleDefinition(reader);
            line = reader.readLine();
        }
    }

    private static void readRuleDefinition(final BufferedReader reader) throws IOException {
        final Automaton automaton = readAutomatonDefinition(reader);

        // Read state and actions
        final String lexerStateString = reader.readLine();
        final List<LexerAction> actions = readRuleActions(reader);
        final LexerRule rule = new LexerRule(lexerStateString, automaton, actions);

        states.get(lexerStateString).addRule(rule);
    }

    private static Automaton readAutomatonDefinition(final BufferedReader reader) throws IOException {
        final String states = reader.readLine();
        final String acceptStates = reader.readLine();
        final String startState = reader.readLine();
        final String alphabet = reader.readLine();
        final List<String> transitions = new ArrayList<String>();

        String line = reader.readLine();
        while (!line.isEmpty()) {
            transitions.add(line);
            line = reader.readLine();
        }

        return new ENFAutomatonGenerator().fromTextDefinition(states, acceptStates, alphabet, transitions, startState);
    }

    private static List<LexerAction> readRuleActions(final BufferedReader reader) throws IOException {
        final List<LexerAction> actions = new ArrayList<LexerAction>();

        reader.readLine(); // Ignore opening bracket.

        // Parse actions
        String line = reader.readLine();
        while (!line.equals("}")) {
            actions.add(ActionFactory.fromString(line));
            line = reader.readLine();
        }

        return actions;
    }
}
