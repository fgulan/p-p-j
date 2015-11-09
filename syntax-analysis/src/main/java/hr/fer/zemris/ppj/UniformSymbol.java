package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.grammar.ProductionParser;
import hr.fer.zemris.ppj.grammar.interfaces.Symbol;

/**
 * <code>UniformSymbol</code> represents a uniform symbol
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class UniformSymbol {

    private final String type;
    private final int lineNumber;
    private final List<Symbol> lexemes = new ArrayList<>();

    /**
     * Class constructor, specifies the uniform symbol.
     *
     * @param definition
     *            textual definition of the uniform symbol.
     * @since 1.0
     */
    public UniformSymbol(final String definition) {

        String[] split = definition.split(" ");
        type = split[0];
        lineNumber = Integer.valueOf(split[1]);

        for (int i = 2; i < split.length; i++) {
            lexemes.add(ProductionParser.parseSymbol(split[i]));
        }
    }

    /**
     * Generates a list of lexemes from the uniform symbol.
     *
     * @return list of lexemes.
     * @since 1.0
     */
    public List<Lexeme> toLexemes() {
        List<Lexeme> lexemes = new ArrayList<>();

        for (Symbol symbol : this.lexemes) {
            lexemes.add(new Lexeme(type, lineNumber, symbol));
        }

        return lexemes;
    }

    @Override
    public String toString() {
        String result = type + " " + lineNumber;
        for (Symbol symbol : lexemes) {
            result += " " + symbol.toString();
        }

        return result;
    }

}
