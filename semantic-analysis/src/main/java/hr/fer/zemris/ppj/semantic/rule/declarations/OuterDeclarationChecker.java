package hr.fer.zemris.ppj.semantic.rule.declarations;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>OuterDeclarationChecker</code> is a checker for outer declaration.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class OuterDeclarationChecker implements Checker {

    // <vanjska_deklaracija> ::= <definicija_funkcije>
    // <vanjska_deklaracija> ::= <deklaracija>

    /**
     * Name of the node.
     */
    public static final String NAME = "<OuterDeclaration>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<vanjska_deklaracija>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 65.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
