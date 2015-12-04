package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>LogicalAndExpressionChecker</code> is a checker for logical and expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class LogicalAndExpressionChecker implements Checker {

    // <log_i_izraz> ::= <bin_ili_izraz>
    // <log_i_izraz> ::= <log_i_izraz> OP_I <bin_ili_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<LogicalAndExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<log_i_izraz>";

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
