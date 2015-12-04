package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>AdditiveExpressionChecker</code> is a checker for aditive expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class AdditiveExpressionChecker implements Checker {

    // <aditivni_izraz> ::= <multiplikativni_izraz>
    // <aditivni_izraz> ::= <aditivni_izraz> PLUS <multiplikativni_izraz>
    // <aditivni_izraz> ::= <aditivni_izraz> MINUS <multiplikativni_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<AdditiveExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<aditivni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 57, 58.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
