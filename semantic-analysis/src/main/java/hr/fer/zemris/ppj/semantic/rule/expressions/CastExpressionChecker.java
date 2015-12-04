package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>CastExpressionChecker</code> is a checker for cast expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class CastExpressionChecker implements Checker {

    // <cast_izraz> ::= <unarni_izraz>
    // <cast_izraz> ::= L_ZAGRADA <ime_tipa> D_ZAGRADA <cast_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<CastExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<cast_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 55, 56.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
