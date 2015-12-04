package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>BinaryXorExpressionChecker</code> is a checker for binary xor expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class BinaryXorExpressionChecker implements Checker {

    // <bin_xili_izraz> ::= <bin_i_izraz>
    // <bin_xili_izraz> ::= <bin_xili_izraz> OP_BIN_XILI <bin_i_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<BinaryXorExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<bin_xili_izraz>";

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
