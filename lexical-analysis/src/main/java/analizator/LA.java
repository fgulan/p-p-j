import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.ppj.lexical.analysis.analyzer.LexerRule;
import hr.fer.zemris.ppj.lexical.analysis.analyzer.LexerState;
import hr.fer.zemris.ppj.lexical.analysis.analyzer.LexicalAnalyzer;
import hr.fer.zemris.ppj.lexical.analysis.analyzer.actions.ActionFactory;
import hr.fer.zemris.ppj.lexical.analysis.analyzer.actions.EnterStateAction;
import hr.fer.zemris.ppj.lexical.analysis.analyzer.actions.LexerAction;
import hr.fer.zemris.ppj.lexical.analysis.analyzer.actions.NewLineAction;
import hr.fer.zemris.ppj.lexical.analysis.analyzer.actions.RejectAction;
import hr.fer.zemris.ppj.lexical.analysis.analyzer.actions.ReturnAction;
import hr.fer.zemris.ppj.lexical.analysis.analyzer.actions.TokenizeAction;
import hr.fer.zemris.ppj.lexical.analysis.automaton.BasicInput;
import hr.fer.zemris.ppj.lexical.analysis.automaton.BasicState;
import hr.fer.zemris.ppj.lexical.analysis.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Input;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transfer.ENFAutomatonTransferFunction;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transfer.EpsilonTransition;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transfer.FAutomatonTransition;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transfer.NormalTransition;

/**
 * <code>LA</code> class is required by the evaluator, to contain a entry point for the lexical analyzer generator.
 *
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
    public static void main(final String[] args) {
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
        new LexicalAnalyzer(states, startState, source, new PrintStream(System.out)).analyze();
        ;
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
        // TODO for now - do we need them?

        // Read empty line
        line = reader.readLine();

        while (!line.equals("END")) {
            readRuleDefinition(reader);
            line = reader.readLine();
        }
    }

    private static void readRuleDefinition(final BufferedReader reader) throws IOException {
        // Read all states
        String line = reader.readLine();

        // Parse all states
        final Set<State> allStates = new TreeSet<>();
        for (final String stateAutomaton : line.split(" ")) {
            allStates.add(new BasicState(stateAutomaton));
        }

        // Parse accepted states
        line = reader.readLine();
        final Set<State> acceptedStates = new TreeSet<>();
        for (final String stateAutomaton : line.split(" ")) {
            acceptedStates.add(new BasicState(stateAutomaton));
        }

        // Parse start state
        line = reader.readLine();
        final State startState = new BasicState(line);

        // Parse alphabet
        line = reader.readLine();
        final Set<Input> alphabet = new HashSet<>();
        for (final String input : line.split(" ")) {
            alphabet.add(new BasicInput(input));
        }

        // Parse transition and create automaton
        final Set<FAutomatonTransition> transitions = readAutomatonTransitions(reader);
        final ENFAutomaton automaton = new ENFAutomaton(allStates, acceptedStates, alphabet,
                new ENFAutomatonTransferFunction(transitions), startState);

        // Read state and actions
        final String lexerStateString = reader.readLine();
        final List<LexerAction> actions = readRuleActions(reader);
        final LexerRule rule = new LexerRule(lexerStateString, automaton, actions);
        states.get(lexerStateString).addRule(rule);
    }

    private static Set<FAutomatonTransition> readAutomatonTransitions(final BufferedReader reader) throws IOException {
        final Set<FAutomatonTransition> transitions = new HashSet<>();
        String line = reader.readLine();
        while (!line.isEmpty()) {
            final String[] args = line.split(" ");
            if ((args.length == 3) && args[1].equals("null")) {
                transitions.add(new EpsilonTransition(new BasicState(args[0]), new BasicState(args[2])));
            }
            else if (args.length == 3) {
                transitions.add(new NormalTransition(new BasicState(args[0]), new BasicState(args[2]),
                        new BasicInput(escapeString(args[1]))));
            }
            line = reader.readLine();
        }
        return transitions;
    }

    private static String escapeString(final String input) {
        String output = input;
        switch (input) {
            case "\\n":
                output = "\n";
                break;
            case "\\r":
                output = "\r";
                break;
            case "\\t":
                output = "\t";
                break;
            case "\\_":
                output = " ";
                break;
            default:
                output = input;
                break;
        }
        return output;
    }

    private static List<LexerAction> readRuleActions(final BufferedReader reader) throws IOException {
        final List<LexerAction> actions = new ArrayList<LexerAction>();

        // Read opening bracket
        String line = reader.readLine();
        // Parse actions
        line = reader.readLine();

        // https://www.youtube.com/watch?v=MEogSTKCgBE
        LexerAction returnAction = null;
        LexerAction tokenizeAction = null;
        LexerAction rejectAction = null;
        LexerAction lineAction = null;
        LexerAction enterStateAction = null;

        while (!line.equals("}")) {
            LexerAction action = ActionFactory.fromString(line);
            if (action instanceof ReturnAction) {
                returnAction = action;
            }
            else if (action instanceof NewLineAction) {
                lineAction = action;
            }
            else if (action instanceof EnterStateAction) {
                enterStateAction = action;
            }
            else if (action instanceof TokenizeAction) {
                tokenizeAction = action;
            }
            else if (action instanceof RejectAction) {
                rejectAction = action;
            }
            line = reader.readLine();
        }
        if (returnAction != null) {
            actions.add(returnAction);
        }
        if (tokenizeAction != null) {
            actions.add(tokenizeAction);
        }
        if (rejectAction != null) {
            actions.add(rejectAction);
        }
        if (lineAction != null) {
            actions.add(lineAction);
        }
        if (enterStateAction != null) {
            actions.add(enterStateAction);
        }
        return actions;
    }
}
