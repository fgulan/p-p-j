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
    
    public static final String SKIP_ACTION = "-";
    public static final String NEW_LINE_ACTION = "NOVI_REDAK";
    public static final String RETURN_ACTION = "VRATI_SE";
    public static final String ENTER_STATE_ACTION = "UDJI_U_STANJE";

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
        
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("test.in"), StandardCharsets.UTF_8));) {
            readSourceCode(reader);
        } catch (Exception e) {
            System.err.println("Unable to read from system input. Message: " + e.getMessage());
        }
        
        try(BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                new FileInputStream("test.in"), 
                StandardCharsets.UTF_8));) 
        {
            readInputData(inputReader);
        } catch (final Exception e) {
            System.err.println("Unable to read from file input. Message: " + e.getMessage());
        }
        new LexicalAnalyzer(states, startState, source, new PrintStream(System.out));
    }
    
    private static void readSourceCode(final BufferedReader reader) throws IOException {
        String line = reader.readLine();
        StringBuilder builder = new StringBuilder();
        while (line != null) {
            builder.append(line);
            builder.append("\n");
            line = reader.readLine();
        }
        source = builder.toString();
    }
    
    private static void readInputData(final BufferedReader reader) throws IOException {
        //Read states
        String line = reader.readLine();

        //Parse states
        for (String stateString : line.split(" ")) {
            LexerState state = new LexerState(stateString);
            if (startState == null) {
                startState = state;
            }
            states.put(stateString, state);
        }
        
        //Read empty line
        line = reader.readLine();
        
        //Read tokens
        line = reader.readLine();
        
        //Parse tokens
        //TODO for now - do we need them?
        
        //Read empty line
        line = reader.readLine();
        
        while (!line.equals("END")) {
            readRuleDefinition(reader);
            line = reader.readLine();
        }
    }

    private static void readRuleDefinition(final BufferedReader reader) throws IOException {
        //Read all states
        String line = reader.readLine();
        
        //Parse all states
        Set<State> allStates = new TreeSet<>();
        for (String stateAutomaton : line.split(" ")) {
            allStates.add(new BasicState(stateAutomaton));
        }
        
        //Parse accepted states
        line = reader.readLine();
        Set<State> acceptedStates = new TreeSet<>();
        for (String stateAutomaton : line.split(" ")) {
            acceptedStates.add(new BasicState(stateAutomaton));
        }
        
        //Parse start state
        line = reader.readLine();
        State startState = new BasicState(line);
        
        //Parse alphabet
        line = reader.readLine();
        Set<Input> alphabet = new TreeSet<>();
        for (Input input : alphabet) {
            alphabet.add(new BasicInput(input));
        }
        
        //Parse transition and create automaton
        Set<FAutomatonTransition> transitions = readAutomatonTransitions(reader);
        ENFAutomaton automaton = new ENFAutomaton(allStates, acceptedStates, alphabet, 
                                                  new ENFAutomatonTransferFunction(transitions), startState);
        
        //Read empty line
        line = reader.readLine();
        
        //Read state and actions
        String lexerStateString = reader.readLine();
        List<LexerAction> actions = readRuleActions(reader);
        LexerRule rule = new LexerRule(automaton, actions);
        states.get(lexerStateString).addRule(rule);
    }
    
    private static Set<FAutomatonTransition> readAutomatonTransitions(final BufferedReader reader) throws IOException {
        Set<FAutomatonTransition> transitions = new HashSet<>();
        String line = reader.readLine();
        while (!line.isEmpty()) {
            String[] args = line.split(" ");
            if (args.length == 3 && args[1].equals("null")) {
                transitions.add(new EpsilonTransition(new BasicState(args[0]), 
                                                      new BasicState(args[2])));
            } else if (args.length == 3) {
                transitions.add(new NormalTransition(new BasicState(args[0]), 
                                                     new BasicState(args[2]), 
                                                     new BasicInput(args[1])));
            }
            line = reader.readLine();
        }
        return transitions;
    }
    
    private static List<LexerAction> readRuleActions(final BufferedReader reader) throws IOException {
        List<LexerAction> actions = new ArrayList<LexerAction>();
        
        //Read opening bracket
        String line = reader.readLine();
        //Parse actions
        line = reader.readLine(); 
        while (!line.equals("}")) {
            if (line.equals("-")) {
                actions.add(new RejectAction());
            } else {
                if (line.startsWith(RETURN_ACTION)) {
                    String[] args = line.split(" ");
                    int offset = Integer.parseInt(args[1]);
                    actions.add(new ReturnAction(offset));
                } else if (line.startsWith(NEW_LINE_ACTION)) {
                    actions.add(new NewLineAction());
                } else if (line.startsWith(ENTER_STATE_ACTION)) {
                    actions.add(new EnterStateAction(line.split(" ")[1]));
                } else {
                    //Print action
                    actions.add(new TokenizeAction(line));
                }
            }
            line = reader.readLine();
        }
        return actions;
    }
}
