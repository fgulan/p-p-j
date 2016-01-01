package hr.fer.zemris.ppj.manipulators.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.interfaces.Manipulator;
import hr.fer.zemris.ppj.types.CharType;
import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.VoidType;

/**
 * <code>TypeSpecifierMtanipulator</code> is a manipulator for type specifier.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class TypeSpecifierManipulator implements Manipulator {

    // <specifikator_tipa> ::= KR_VOID
    // <specifikator_tipa> ::= KR_CHAR
    // <specifikator_tipa> ::= KR_INT

    /**
     * Name of the node.
     */
    public static final String NAME = "<TypeSpecifier>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<specifikator_tipa>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 56.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {

        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();

        if ("KR_VOID".equals(firstSymbol)) {
            node.addAttribute(Attribute.TYPE, new VoidType());
        }
        if ("KR_CHAR".equals(firstSymbol)) {
            node.addAttribute(Attribute.TYPE, new CharType());
        }
        if ("KR_INT".equals(firstSymbol)) {
            node.addAttribute(Attribute.TYPE, new IntType());
        }

        return true;
    }

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case TYPE_SPECIFIER_1: {
                break;
            }

            case TYPE_SPECIFIER_2: {
                break;
            }

            case TYPE_SPECIFIER_3: {
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
