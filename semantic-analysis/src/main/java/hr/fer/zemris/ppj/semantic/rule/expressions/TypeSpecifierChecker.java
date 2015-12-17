package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.types.CharType;
import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.VoidType;

/**
 * <code>TypeSpecifierMtanipulator</code> is a checker for type specifier.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class TypeSpecifierChecker implements Checker {

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
     * @since alpha
     */
    @Override
    public boolean check(Node node) {

        Node firstChild = node.getChild(0);
        String firstSymbol = firstChild.name();

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

}
