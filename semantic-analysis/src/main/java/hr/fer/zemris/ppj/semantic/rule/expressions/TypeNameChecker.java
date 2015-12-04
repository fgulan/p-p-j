package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>TypeNameChecker</code> is a checker for type name.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class TypeNameChecker implements Checker {

    // <ime_tipa> ::= <specifikator_tipa>
    // <ime_tipa> ::= KR_CONST <specifikator_tipa>

    /**
     * Name of the node.
     */
    public static final String NAME = "<TypeName>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<ime_tipa>";

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
