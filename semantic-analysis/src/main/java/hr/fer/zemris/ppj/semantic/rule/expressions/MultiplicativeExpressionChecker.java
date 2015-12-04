package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>MultiplicativeExpressionChecker</code> is a checker for multiplicative expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class MultiplicativeExpressionChecker implements Checker {

    // <multiplikativni_izraz> ::= <cast_izraz>
    // <multiplikativni_izraz> ::= <multiplikativni_izraz> OP_PUTA <cast_izraz>
    // <multiplikativni_izraz> ::= <multiplikativni_izraz> OP_DIJELI <cast_izraz>
    // <multiplikativni_izraz> ::= <multiplikativni_izraz> OP_MOD <cast_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<MultiplicativeExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<multiplikativni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 57.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
