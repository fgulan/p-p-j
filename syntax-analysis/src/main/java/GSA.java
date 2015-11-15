import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.finite.automaton.DFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.transforms.DFAConverter;
import hr.fer.zemris.ppj.grammar.Grammar;
import hr.fer.zemris.ppj.grammar.GrammarBuilder;
import hr.fer.zemris.ppj.grammar.ProductionParser;
import hr.fer.zemris.ppj.lr1.parser.LR1ParserTable;
import hr.fer.zemris.ppj.lr1.parser.LR1ParserTableFactory;
import hr.fer.zemris.ppj.lr1.parser.ParserBuilder;

/**
 * <code>GSA</code> class is required by the evaluator, to contain a entry point for the syntax analyzer generator.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class GSA {

    private static final List<String> nonterminalSymbols = new ArrayList<>();
    private static final List<String> terminalSymbols = new ArrayList<>();
    private static final List<String> syncSymbols = new ArrayList<>();
    private static Grammar grammar;

    /**
     * Entry point for the syntax analyzer generator.
     *
     * @param args
     *            command line arguments aren't used
     * @since alpha
     */
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("test.san")))) {
            readInputData(reader);
        }
        catch (final IOException e) {
            System.err.println(e.getMessage());
        }

        try (OutputStreamWriter writer = new FileWriter(new File("analizator/definition.txt"))) {
            ENFAutomaton enfa = ParserBuilder.fromLR1Grammar(grammar);
            DFAutomaton automaton = new DFAConverter().transform(enfa);
            LR1ParserTable parserTable = LR1ParserTableFactory.fromDFA(automaton, ProductionParser.parseSymbol("<%>"));
            writeParserTable(parserTable, writer);
        }
        catch (final IOException e) {
            System.err.println(e.getMessage());
        }

    }

    private static void readInputData(BufferedReader reader) throws IOException {
        String originalStartSymbol = null;
        nonterminalSymbols.add("<%>");
        for (String symbol : reader.readLine().substring(3).split(" ")) {
            nonterminalSymbols.add(symbol);
            if (originalStartSymbol == null) {
                originalStartSymbol = symbol;
            }
        }

        for (String symbol : reader.readLine().substring(3).split(" ")) {
            terminalSymbols.add(symbol);
        }

        for (String symbol : reader.readLine().substring(5).split(" ")) {
            syncSymbols.add(symbol);
        }

        GrammarBuilder builder = new GrammarBuilder(nonterminalSymbols, terminalSymbols, "<%>");
        builder.addProduction(ProductionParser.fromText("<%> " + originalStartSymbol));
        String line = reader.readLine();
        do {
            String leftSide = line;
            do {
                String rightSide = line = reader.readLine();
                if ((line != null) && rightSide.startsWith(" ")) {
                    builder.addProduction(ProductionParser.fromText(leftSide, rightSide.trim()));
                }
            } while ((line != null) && !line.startsWith("<"));
        } while (line != null);
        grammar = builder.build();
    }

    private static void writeParserTable(LR1ParserTable table, OutputStreamWriter writer) throws IOException {
        for (String symbol : nonterminalSymbols) {
            writer.write(symbol + " ");
        }
        writer.write("\n");
        for (String symbol : terminalSymbols) {
            writer.write(symbol + " ");
        }
        writer.write("\n");
        for (String symbol : syncSymbols) {
            writer.write(symbol + " ");
        }
        writer.write("\n");
        writer.write(table.toString());
    }

}
