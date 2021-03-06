package hr.fer.zemris.ppj.manipulators.declarations;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.interfaces.Manipulator;
import hr.fer.zemris.ppj.types.Type;
import hr.fer.zemris.ppj.types.VoidType;

/**
 * <code>ParameterDeclarationManipulator</code> is a manipulator for parameter declaration.
 *
 * @author Domagoj Polancec
 *
 * @version 1.1
 */
public class ParameterDeclarationManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<ParameterDeclaration>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<deklaracija_parametra>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 67.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {

        final Node typeNode = node.getChild(0);
        final Node idnNode = node.getChild(1);

        if (!typeNode.check() || !idnNode.check()) {
            return Utils.badNode(node);
        }

        Type type = (Type) typeNode.getAttribute(Attribute.TYPE);
        if (type.equals(new VoidType())) {
            return Utils.badNode(node);
        }
        final String name = (String) idnNode.getAttribute(Attribute.VALUE);

        if (node.childrenCount() > 2) {
            type = type.toArray();
        }

        node.addAttribute(Attribute.TYPE, type);
        node.addAttribute(Attribute.VALUE, name);

        return true;
    }

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
        case PARAMETER_DECLARATION_1: {
            // PARAMETER_DECLARATION_1("<deklaracija_parametra> ::= <ime_tipa> IDN"),
            node.getChild(0).generate();
            break;
        }

        case PARAMETER_DECLARATION_2: {
            // PARAMETER_DECLARATION_2("<deklaracija_parametra> ::= <ime_tipa> IDN L_UGL_ZAGRADA D_UGL_ZAGRADA"),
            node.getChild(0).generate();
            break;
        }

        default:
            System.err.println("Generation reached undefined production!");
            break;
        }
    }
}
