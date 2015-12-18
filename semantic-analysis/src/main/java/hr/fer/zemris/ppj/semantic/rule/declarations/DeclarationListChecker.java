package hr.fer.zemris.ppj.semantic.rule.declarations;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>DeclarationListChecker</code> is a checker for declaration list.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
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
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {

        for (final Node current : node.getChildren()) {
            if (!current.check()) {
                return Utils.badNode(node);
            }
        }

        return true;
    }

}
