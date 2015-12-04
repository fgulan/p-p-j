package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>BinaryOrExpressionChecker</code> is a checker for binary or expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class BinaryOrExpressionChecker implements Checker {

    // <bin_ili_izraz> ::= <bin_xili_izraz>
    // <bin_ili_izraz> ::= <bin_ili_izraz> OP_BIN_ILI <bin_xili_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<BinaryOrExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<bin_ili_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 60.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
