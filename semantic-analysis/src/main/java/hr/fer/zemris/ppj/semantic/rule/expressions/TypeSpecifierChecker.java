package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

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
        // TODO Auto-generated method stub
        return false;
    }

}
