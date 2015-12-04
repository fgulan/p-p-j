package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>PrimaryExpressionChecker</code> is a checker for primary expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class PrimaryExpressionChecker implements Checker {

    // <primarni_izraz> ::= IDN
    // <primarni_izraz> ::= BROJ
    // <primarni_izraz> ::= ZNAK
    // <primarni_izraz> ::= NIZ_ZNAKOVA
    // <primarni_izraz> ::= L_ZAGRADA <izraz> D_ZAGRADA

    /**
     * Name of the node.
     */
    public static final String NAME = "<PrimaryExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<primarni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 51, 52.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
