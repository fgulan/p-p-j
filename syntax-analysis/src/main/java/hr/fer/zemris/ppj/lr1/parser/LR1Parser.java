package hr.fer.zemris.ppj.lr1.parser;

import java.io.PrintStream;
import java.util.List;

import hr.fer.zemris.ppj.Lexeme;

/**
 * <code>LR1Parser</code> represents a canonical LR(1) parser.
 *
 * @author Matea Sabolic
 *
 * @version alpha
 */
public class LR1Parser {

    /**
     * Class constructor, specifies the actions of the parser.
     *
     * @param table
     *            the table of actions.
     * @since alpha
     */
    public LR1Parser(final LR1ParserActionTable table) {
        // TODO: needs to be implemented. @Matea
    }

    /**
     * Analyzes the list of lexemes.
     *
     * @param lexemes
     *            the lexemes.
     * @param outputStream
     *            the output stream.
     * @param errorStream
     *            the error output stream.
     * @since alpha
     */
    public void analyze(List<Lexeme> lexemes, PrintStream outputStream, PrintStream errorStream) {
        // TODO: needs to be implemented. @Matea
    }

}
