package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>LogicalOrExpressionChecker</code> is a checker for logical or expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class LogicalOrExpressionChecker implements Checker {

    // <log_ili_izraz> ::= <log_i_izraz>
    // <log_ili_izraz> ::= <log_ili_izraz> OP_ILI <log_i_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<LogicalOrExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<log_ili_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 60, 61.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
