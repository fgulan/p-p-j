package hr.fer.zemris.ppj.manipulators.declarations;

import java.util.Stack;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>DeclarationListManipulator</code> is a manipulator for declaration list.
 *
 * @author Domagoj Polancec
 *
 * @version 1.1
 */
public class DeclarationListManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

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
            // DECLARATION_LIST_1("<lista_deklaracija> ::= <deklaracija>"),
            // DECLARATION_LIST_2("<lista_deklaracija> ::= <lista_deklaracija> <deklaracija>")
            case DECLARATION_LIST_1:
            case DECLARATION_LIST_2: {
                Stack<Node> declarationNodeStack = new Stack<>();
                Node temp = node;
                while (Production.fromNode(temp) == Production.DECLARATION_LIST_2) {
                    declarationNodeStack.push(temp.getChild(1));
                    temp = temp.getChild(0);
                }
                declarationNodeStack.push(temp.getChild(0));

                while (!declarationNodeStack.isEmpty()) {
                    declarationNodeStack.pop().generate();
                }
                break;
            }
            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }

}
