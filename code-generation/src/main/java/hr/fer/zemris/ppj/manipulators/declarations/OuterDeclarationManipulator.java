package hr.fer.zemris.ppj.manipulators.declarations;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>OuterDeclarationManipulator</code> is a manipulator for outer declaration.
 *
 * @author Domagoj Polancec
 *
 * @version 1.1
 */
public class OuterDeclarationManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

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
        switch (Production.fromNode(node)) {
            case OUTER_DECLARATION_1: {
                break;
            }

            case OUTER_DECLARATION_2: {
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
