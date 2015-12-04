package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>RelationalExpressionChecker</code> is a checker for relational expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class RelationalExpressionChecker implements Checker {

    // <odnosni_izraz> ::= <aditivni_izraz>
    // <odnosni_izraz> ::= <odnosni_izraz> OP_LT <aditivni_izraz>
    // <odnosni_izraz> ::= <odnosni_izraz> OP_GT <aditivni_izraz>
    // <odnosni_izraz> ::= <odnosni_izraz> OP_LTE <aditivni_izraz>
    // <odnosni_izraz> ::= <odnosni_izraz> OP_GTE <aditivni_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<RelationalExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<odnosni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 58.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
