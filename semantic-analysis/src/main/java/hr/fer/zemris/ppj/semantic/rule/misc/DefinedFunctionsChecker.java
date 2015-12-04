package hr.fer.zemris.ppj.semantic.rule.misc;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>DefinedFunctionChecker</code> is a checker for defined functions.
 *
 * @author Matea Sabolic
 *
 * @version alpha
 */
public class DefinedFunctionsChecker implements Checker {

    // <prijevodna_jedinica> ::= <vanjska_deklaracija>
    // <prijevodna_jedinica> ::= <prijevodna_jedinica> <vanjska_deklaracija>

    /**
     * Name of the node.
     */
    public static final String NAME = "<DefinedFunctions";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<funkcija>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 72.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
