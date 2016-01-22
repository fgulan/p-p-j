package hr.fer.zemris.ppj.manipulators.declarations;

import java.util.Stack;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>DeclaratorInitializationListManipulator</code> is a manipulator for declarator initialization list.
 *
 * @author Domagoj Polancec
 *
 * @version 1.1
 */
public class DeclaratorInitializationListManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<DeclaratorInitializationList>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<lista_init_deklaratora>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 68.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {

        for (final Node child : node.getChildren()) {
            child.addAttribute(Attribute.ITYPE, node.getAttribute(Attribute.ITYPE));
            if (!child.check()) {
                return Utils.badNode(child);
            }
        }

        return true;
    }

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case DECLARATOR_INITIALIZATION_LIST_1:
            case DECLARATOR_INITIALIZATION_LIST_2: {
                Stack<Node> initDeclaratorStack = new Stack<>();
                Node temp = node;
                while (Production.fromNode(temp) == Production.DECLARATOR_INITIALIZATION_LIST_2) {
                    initDeclaratorStack.push(temp.getChild(2));
                    temp = temp.getChild(0);
                }
                initDeclaratorStack.push(temp.getChild(0));

                while (!initDeclaratorStack.isEmpty()) {
                    initDeclaratorStack.pop().generate();
                }

                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
