import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.grammar.Grammar;
import hr.fer.zemris.ppj.grammar.GrammarBuilder;
import hr.fer.zemris.ppj.grammar.ProductionParser;

/**
 * <code>GSA</code> class is required by the evaluator, to contain a entry point for the syntax analyzer generator.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class GSA {

    private static final Set<String> nonterminalSymbols = new HashSet<>();
    private static final Set<String> terminalSymbols = new HashSet<>();
    private static final Set<String> syncSymbols = new HashSet<>();
    private static Grammar grammar;

    /**
     * Entry point for the syntax analyzer generator.
     *
     * @param args
     *            command line arguments aren't used
     * @since alpha
     */
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            readInputData(reader);
        }
        catch (final IOException e) {
            System.err.println(e.getMessage());
        }

        try (OutputStreamWriter writer = new FileWriter(new File("analizator/definition.txt"))) {
            // writeAnalyzerDefinition(writer, new AnalyzerDefinition(analyzerStates, lexemeNames, rules));
        }
        catch (final IOException e) {
            System.err.println(e.getMessage());
        }

    }

    private static void readInputData(BufferedReader reader) throws IOException {
        String originalStartSymbol = null;
        for (String symbol : reader.readLine().substring(3).split(" ")) {
            nonterminalSymbols.add(symbol);
            if (originalStartSymbol == null) {
                originalStartSymbol = symbol;
            }
        }
        nonterminalSymbols.add("<$>");

        for (String symbol : reader.readLine().substring(3).split(" ")) {
            terminalSymbols.add(symbol);
        }

        for (String symbol : reader.readLine().substring(3).split(" ")) {
            syncSymbols.add(symbol);
        }

        GrammarBuilder builder = new GrammarBuilder(nonterminalSymbols, terminalSymbols, "<$>");
        String line = reader.readLine();
        do {
            String leftSide = line;
            do {
                String rightSide = reader.readLine();
                line = reader.readLine();
                builder.addProduction(ProductionParser.fromText(leftSide, rightSide));
            } while ((line != null) && line.startsWith(" "));
        } while (line != null);

        builder.addProduction(ProductionParser.fromText("<$> " + originalStartSymbol));

        grammar = builder.build();
    }

}
