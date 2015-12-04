package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>UnaryExpressionChecker</code> is a checker for unary expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class UnaryExpressionChecker implements Checker {

    // <unarni_izraz> ::= <postfiks_izraz>
    // <unarni_izraz> ::= OP_INC <unarni_izraz>
    // <unarni_izraz> ::= OP_DEC <unarni_izraz>
    // <unarni_izraz> ::= <unarni_operator> <cast_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<UnaryExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<unarni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 54, 55.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
