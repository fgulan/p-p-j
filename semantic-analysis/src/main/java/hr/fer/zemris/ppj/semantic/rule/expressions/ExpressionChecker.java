package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>ExpressionChecker</code> is a checker for expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class ExpressionChecker implements Checker {

    // <izraz> ::= <izraz_pridruzivanja>
    // <izraz> ::= <izraz> ZAREZ <izraz_pridruzivanja>

    /**
     * Name of the node.
     */
    public static final String NAME = "<Expression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 61, 62.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
