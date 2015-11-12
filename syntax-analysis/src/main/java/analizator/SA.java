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
import hr.fer.zemris.ppj.lr1.parser.LR1Parser;
import hr.fer.zemris.ppj.lr1.parser.LR1ParserTable;

/**
 * <code>SA</code> class is required by the evaluator, to contain a entry point for the syntax analyzer.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class SA {

    private static final List<Lexeme> lexemes = new ArrayList<>();
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

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("table.txt")))) {
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

        new LR1Parser(parserTable).analyze(lexemes, new PrintStream(System.out), new PrintStream(System.err));
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
    }

    /*
     * Reads the LR(1) parser action table.
     */
    private static void readParserActionTable(BufferedReader reader) throws IOException {
        // TODO: Wait for Domagoj
    }
}
