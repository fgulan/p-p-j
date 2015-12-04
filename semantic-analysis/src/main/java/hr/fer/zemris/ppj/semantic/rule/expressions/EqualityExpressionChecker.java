package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>EqualityExpressionChecker</code> is a checker for equality expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class EqualityExpressionChecker implements Checker {

    // <jedankosni_izraz> ::= <odnosni_izraz>
    // <jednakosni_izraz> ::= <jednakosni_izraz> OP_EQ <odnosni_izraz>
    // <jednakosni_izraz> ::= <jednakosni_izraz> OP_NEQ <odnosni_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<EqualityExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<jednakosni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 58, 59.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
