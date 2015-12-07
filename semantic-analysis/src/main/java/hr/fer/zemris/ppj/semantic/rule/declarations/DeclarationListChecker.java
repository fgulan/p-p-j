package hr.fer.zemris.ppj.semantic.rule.declarations;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>DeclarationListChecker</code> is a checker for declaration list.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class DeclarationListChecker implements Checker {

    // <lista_deklaracija> ::= <deklaracija>
    // <lista_deklaracija> ::= <lista_deklaracija> <deklaracija>

    /**
     * Name of the node.
     */
    public static final String NAME = "<DeclarationList>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<lista_deklaracija>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 68.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}