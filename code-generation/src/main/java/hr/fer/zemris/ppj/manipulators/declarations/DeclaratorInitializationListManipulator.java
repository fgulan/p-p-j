package hr.fer.zemris.ppj.manipulators.declarations;

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
            case DECLARATOR_INITIALIZATION_LIST_1: {
//                DECLARATOR_INITIALIZATION_LIST_1("<lista_init_deklaratora> ::= <init_deklarator>"),
                node.getChild(0).generate();
                break;
            }

            case DECLARATOR_INITIALIZATION_LIST_2: {
//                DECLARATOR_INITIALIZATION_LIST_2("<lista_init_deklaratora> ::= <lista_init_deklaratora> ZAREZ <init_deklarator>"),
                node.getChild(0).generate();
                node.getChild(2).generate();
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
