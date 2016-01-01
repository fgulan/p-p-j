package hr.fer.zemris.ppj.manipulators.declarations;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>DeclarationListChecker</code> is a checker for declaration list.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class DeclarationListManipulator implements Manipulator {

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

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case DECLARATION_LIST_1: {
                break;
            }

            case DECLARATION_LIST_2: {
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }

}
