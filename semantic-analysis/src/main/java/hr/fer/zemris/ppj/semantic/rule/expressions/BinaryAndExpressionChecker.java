package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>BinaryAndExpressionChecker</code> is a checker for binary and expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class BinaryAndExpressionChecker implements Checker {

    // <bin_i_izraz> ::= <jednakosni_izraz>
    // <bin_i_izraz> ::= <bin_i_izraz> OP_BIN_I <jednakosni_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<BinaryAndExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<bin_i_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 59.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
