import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.Lexeme;
import hr.fer.zemris.ppj.grammar.ProductionParser;
import hr.fer.zemris.ppj.grammar.interfaces.Symbol;
import hr.fer.zemris.ppj.lr1.parser.LR1Parser;
import hr.fer.zemris.ppj.lr1.parser.LR1ParserTable;
import hr.fer.zemris.ppj.lr1.parser.LR1ParserTableBuilder;
import hr.fer.zemris.ppj.lr1.parser.Node;
import hr.fer.zemris.ppj.lr1.parser.actions.ActionFactory;
import hr.fer.zemris.ppj.lr1.parser.actions.ParserAction;

/**
 * <code>SA</code> class is required by the evaluator, to contain a entry point for the syntax analyzer.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class SA {

    private static final List<Lexeme> lexemes = new ArrayList<>();

    private static final List<Symbol> nonterminalSymbols = new ArrayList<>();
    private static final List<Symbol> terminalSymbols = new ArrayList<>();
    private static final List<Symbol> syncSymbols = new ArrayList<>();
    private static LR1ParserTable parserTable;

    /**
     * Entry point for the syntax analyzer.
     *
     * @param args
     *            command line arguments aren't used
     * @since alpha
     */
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            readUniformSymbols(reader);
        }
        catch (IOException e) {
            System.err.println("io on reading lexemes");
            System.exit(0);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("definition.txt")))) {
            for (String symbol : reader.readLine().split(" ")) {
                nonterminalSymbols.add(ProductionParser.parseSymbol(symbol));
            }

            for (String symbol : reader.readLine().split(" ")) {
                terminalSymbols.add(ProductionParser.parseSymbol(symbol));
            }

            for (String symbol : reader.readLine().split(" ")) {
                syncSymbols.add(ProductionParser.parseSymbol(symbol));
            }
            syncSymbols.add(ProductionParser.parseSymbol("#"));

            readParserActionTable(reader);
        }
        catch (FileNotFoundException e) {
            System.err.println("no parser table");
            System.exit(0);
        }
        catch (IOException e) {
            System.err.println("io on reading parser table");
            System.exit(0);
        }

        LR1Parser parser = new LR1Parser(parserTable, syncSymbols, nonterminalSymbols.get(0));
        Node tree = parser.analyze(lexemes, new PrintStream(System.out), new PrintStream(System.err));
        System.out.print(tree.print(0));
    }

    /*
     * Reads uniform symbols and parses them to lexemes.
     */
    private static void readUniformSymbols(BufferedReader reader) throws IOException {
        String uniformSymbol = reader.readLine();
        do {
            String[] split = uniformSymbol.split(" ", 3);
            lexemes.add(new Lexeme(split[0], Integer.valueOf(split[1]), ProductionParser.parseSymbol(split[2])));
            uniformSymbol = reader.readLine();
        } while (uniformSymbol != null);
        lexemes.add(new Lexeme("#", -1, ProductionParser.parseSymbol("#")));
    }

    /*
     * Reads the LR(1) parser action table.
     */
    private static void readParserActionTable(BufferedReader reader) throws IOException {
        LR1ParserTableBuilder builder = new LR1ParserTableBuilder();
        String line = reader.readLine();
        while (line != null) {
            String stateID = line.trim();
            line = reader.readLine();
            while ((line != null) && line.startsWith(" ")) {
                String[] split = line.trim().split(" ", 2);
                Symbol symbol = ProductionParser.parseSymbol(split[0]);
                ParserAction action = ActionFactory.fromString(split[1]);
                builder.addAction(new LR1ParserTable.TablePair(stateID, symbol), action);
                line = reader.readLine();
            }
        }
        parserTable = builder.build();
    }
}
