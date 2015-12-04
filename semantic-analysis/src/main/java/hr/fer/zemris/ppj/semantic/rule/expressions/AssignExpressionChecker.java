package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>AssignExpressionChecker</code> is a checker for assign expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class AssignExpressionChecker implements Checker {

    // <izraz_pridruzivanja> ::= <log_ili_izraz>
    // <izraz_pridruzivanja> ::= <postfiks_izraz> OP_PRIDRUZI <izraz_pridruzivanja>

    /**
     * Name of the node.
     */
    public static final String NAME = "<AssignExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<izraz_pridruzivanja>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 61.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
