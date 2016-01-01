package hr.fer.zemris.ppj.manipulators.declarations;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>OuterDeclarationChecker</code> is a checker for outer declaration.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class OuterDeclarationManipulator implements Manipulator {

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
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {

        if (!node.getChild(0).check()) {
            Utils.badNode(node);
        }

        return true;
    }

    @Override
    public void generate(Node node) {
        // TODO Auto-generated method stub

    }
}
