package hr.fer.zemris.ppj.manipulators.declarations;

import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>DeclarationManipulator</code> is a manipulator for declaration.
 *
 * @author Domagoj Polancec
 *
 * @version 1.1
 */
public class DeclarationManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<Declaration>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<deklaracija>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 68.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final List<Node> children = node.getChildren();

        final Node typeNode = children.get(0);
        final Node initDeclListNode = children.get(1);

        if (!typeNode.check()) {
            return Utils.badNode(typeNode);
        }

        initDeclListNode.addAttribute(Attribute.ITYPE, typeNode.getAttribute(Attribute.TYPE));
        if (!children.get(1).check()) {
            return Utils.badNode(initDeclListNode);
        }

        return true;
    }

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case DECLARATION_1: {
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }

}
