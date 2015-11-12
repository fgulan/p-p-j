package hr.fer.zemris.ppj.grammar.interfaces;

/**
 * <code>Symbol</code> is a interface for symbols of the context free grammar.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public interface Symbol{

    /**
     * Checks if the symbol is a terminal symbol of the grammar.
     *
     * @return <code>true</code> if the symbol is a terminal symbol, <code>false</code> otherwise.
     */
    boolean isTerminal();

    /**
     * Returns the name of the symbol.
     * 
     * @return the name.
     */
    String name();
}
